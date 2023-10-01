package com.youngtechcr.www.product.image;

import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.product.ProductService;
import com.youngtechcr.www.storage.DualNameFileCarrier;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepo;
    private final ProductService productService;
    private final ProductImageStorageService imageStorageService;

    private static final Logger log = LoggerFactory.getLogger(ProductImageService.class);
    public ProductImageService(
            ProductImageRepository productImageRepo,
            ProductService productService,
            ProductImageStorageService imageStorageService) {
        this.productImageRepo = productImageRepo;
        this.productService = productService;
        this.imageStorageService = imageStorageService;
    }

    @Transactional(readOnly = true)
    public ProductImage findImageById(Integer imageMetaDataId) {
        return this
                .productImageRepo
                .findById(imageMetaDataId)
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages.
                        NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Transactional(readOnly = true)
    public List<ProductImage> findImages(Integer productId) {
        return productService.findById(productId).getImages();
    }
    @Transactional(readOnly = true)
    public List<ProductImage> findImages(String name) {
        return productService.findByName(name).getImages();
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ProductImage uploadProductImageByProduct(
        Integer productId, MultipartFile image, @Nullable boolean main) {
        Product relatedProduct = productService.findById(productId);
        if(main && this.hasMainImage(relatedProduct)) {
           throw new AlreadyExistsException(
                   HttpErrorMessages.CANT_CREATE_DUPLICATE_MAIN_IMAGE
           );
        }
        ProductImage storedImage = imageStorageService.store(relatedProduct, image);
        storedImage.main(main);
        ProductImage modifiedImage = productImageRepo.save(storedImage);
        log.info("Modified recently stored file, from this -> " + storedImage + " ,to this -> " + modifiedImage);
        return modifiedImage;
    }

    @Transactional(readOnly = true)
    public DualNameFileCarrier downloadImageByProduct(
            Integer productId, Integer productImageId) {
        if(productService.existsProductById(productId)) {
            ProductImage productImage = findImageById(productImageId);
            Resource obtainedImageResource = this.imageStorageService.obtain(productImage);
            log.debug("Obtained/retrieved image from file system with metadata -> " + productImage);
            String originalFileName = productImage.getOriginalName();
            MediaType imageMediaType = MediaType.parseMediaType(productImage.getMimeType());
            return new DualNameFileCarrier(
                    obtainedImageResource, originalFileName, imageMediaType);
        }
        throw new NoDataFoundException(HttpErrorMessages.
                NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    @Transactional(readOnly = true)
    public DualNameFileCarrier downloadMainImageByProduct(Product productToBeInspected ) {
        if(this.hasMainImage(productToBeInspected)) {
            ProductImage mainImg = this.obtainMainImage(productToBeInspected);
            String originalImgName = mainImg.getOriginalName();
            MediaType imageMediaType = MediaType.parseMediaType(mainImg.getMimeType());
            Resource obtainedImageResource = this.
                    imageStorageService.obtain(mainImg);
            log.debug("Downloaded image with metadata -> " + mainImg);
            return new DualNameFileCarrier(obtainedImageResource,
                    originalImgName, imageMediaType);
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_MAIN_ELEMENT_WAS_FOUND);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void deleteImageByProduct(Integer productId, Integer productImageId) {
        if(productService.existsProductById(productId)) {
            ProductImage toBeDeleted = findImageById(productImageId);
            this.imageStorageService.dump(toBeDeleted);
            log.info("Deleted product image with metadata -> " + toBeDeleted );
        }
        throw new NoDataFoundException(HttpErrorMessages
                .NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }



    public ProductImage obtainMainImage(Product product) {
        return product
                .getImages()
                .stream()
                .filter( image -> image.main())
                .findAny()
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages
                        .NO_MAIN_ELEMENT_WAS_FOUND));
    }

    public boolean hasMainImage(Product product) {
        return product
                .getImages()
                .stream()
                .anyMatch((image) -> image.main());
    }

    @Transactional(readOnly = true)
    public ProductImage obtainMainImage(int id) {
        return productService.findById(id)
                .getImages()
                .stream()
                .filter( image -> image.main())
                .findAny()
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages
                        .NO_MAIN_ELEMENT_WAS_FOUND));
    }
}
