package com.youngtechcr.www.product;

import com.youngtechcr.www.brand.Brand;
import com.youngtechcr.www.brand.BrandService;
import com.youngtechcr.www.storage.DualFilenameBearer;
import com.youngtechcr.www.product.image.ProductImage;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.product.image.ProductImageService;
import com.youngtechcr.www.product.image.ProductImageStorageService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.domain.TimestampedUtils;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductValidator validationService;
    private final ProductImageStorageService productImageStorageService;
    private final ProductImageService imageMetaDataService;
    private final BrandService brandService;

    public ProductService(
            ProductRepository productRepository,
            ProductValidator validationService,
            ProductImageStorageService productImageStorageService,
            ProductImageService imageMetaDataService,
            BrandService brandService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
        this.productImageStorageService = productImageStorageService;
        this.imageMetaDataService = imageMetaDataService;
        this.brandService = brandService;
    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer productId) {
        return this
                .productRepository
                .findById(productId)
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Transactional
    public Product createProduct(Product productToBeCreated) {
        if(this.validationService.isValid(productToBeCreated)) {
            TimestampedUtils.setTimestampsToNow(productToBeCreated);
            var createdProduct = this.productRepository.save(productToBeCreated);
            log.info("Created new products" + createdProduct);
            return createdProduct;
        }
        throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT);
    }

    @Transactional
    public Product updateProductById(Integer productId, Product productToBeUpdated) {
        if(productToBeUpdated.getProductId().equals(productId)){
            LocalDateTime storedCreatedAtTimestamp = this.findProductById(productId).getCreatedAt();
            TimestampedUtils.updateTimeStamps(productToBeUpdated, storedCreatedAtTimestamp);
            Product updatedProduct = this.productRepository.save(productToBeUpdated);
            log.info("Updated product: " + updatedProduct);
            return updatedProduct;
        }
        throw new ValueMismatchException(HttpErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Transactional
    public void deleteProductById(Integer productId) {
        if(this.productRepository.existsById(productId)) {
            this.productRepository.deleteById(productId);
            log.info("Deleted product with id: " +  productId);
            return;
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    @Transactional(readOnly = true)
    public Product findProductByBrandId(Integer productId, Integer brandId) {
        Brand requestedBrand = brandService.findById(brandId);
        Product requestedProduct = findProductById(productId);
        if(requestedProduct.getBrand().equals(requestedBrand)) {
            return requestedProduct;
        }
        throw new ValueMismatchException(HttpErrorMessages.REQUESTED_CHILD_ELEMENT_DOESNT_EXIST);
    }


    @Transactional
    public ProductImage uploadProductImageByProductId(
            Integer productId,
            MultipartFile imageToBeUploaded,
            @Nullable ProductImage imageMetaData) {
        if (!this.productRepository.existsById(productId)) {
            throw new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
        }
        ProductImage uploadedProductImage = this
                .productImageStorageService
                .storeProductImage(productId, imageToBeUploaded, imageMetaData);
        return uploadedProductImage;
    }

    @Transactional(readOnly = true)
    public List<ProductImage> getProductImagesMetadataById(Integer productId) {
        return this.findProductById(productId).getImageList();
    }

    public DualFilenameBearer downloadMainProductImageByProductId(Integer productId) {
        var productToBeInspected = this.findProductById(productId);
        if(this.hasMainImage(productToBeInspected)) {
            ProductImage mainImage = this.obtainMainImage(productToBeInspected);
            String originaImageName = mainImage.getOriginalFileName();
            MediaType imageMediaType = MediaType.parseMediaType(mainImage.getMimeType());
            Resource obtainedImageResource = this.productImageStorageService.obtainProductImage(mainImage);
            return new DualFilenameBearer(obtainedImageResource, originaImageName, imageMediaType);
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_MAIN_ELEMENT_WAS_FOUND);
    }

    @Transactional(readOnly = true)
    public DualFilenameBearer downloadProductImageByProductId(Integer productId, Integer productImageId) {
        if(this.productRepository.existsById(productId)) {
            ProductImage productImageMetaData = this.imageMetaDataService.findImageMetaDataById(productImageId);
            Resource obtainedImageResource = this.productImageStorageService.obtainProductImage(productImageMetaData);
            String originalFileName = productImageMetaData.getOriginalFileName();
            MediaType imageMediaType = MediaType.parseMediaType(productImageMetaData.getMimeType());
            return new DualFilenameBearer(obtainedImageResource, originalFileName, imageMediaType);
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    public boolean hasMainImage(Product productToBeInspected) {
        return productToBeInspected
                .getImageList()
                .stream()
                .anyMatch((image) -> image.isMain());
    }

    public ProductImage obtainMainImage(Product productToBeInspected) {
        return productToBeInspected
                .getImageList()
                .stream()
                .filter( image -> image.isMain())
                .findAny()
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages.NO_MAIN_ELEMENT_WAS_FOUND));
    }

    public void deleteProductImageByProduct(Integer productId, Integer productImageId) {
        if(this.productRepository.existsById(productId)) {
            ProductImage productImageToBeDeleted = this.imageMetaDataService.findImageMetaDataById(productImageId);
            this.productImageStorageService.eliminateProductImageCompletely(productImageToBeDeleted);
        }
        throw new NoDataFoundException(HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }
}
