package com.youngtechcr.www.services;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.DoubleNameFileCarrier;
import com.youngtechcr.www.domain.storage.ProductImageMetaData;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.repositories.ProductRepository;
import com.youngtechcr.www.services.storage.ProductImageStorageService;
import com.youngtechcr.www.utils.ErrorMessages;
import com.youngtechcr.www.utils.TimestampUtils;
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
    private ProductRepository productRepository;
    private ValidationService validationService;
    private ProductImageStorageService productImageStorageService;
    private ProductImageMetaDataService imageMetaDataService;

    public ProductService(
            ProductRepository productRepository,
            ValidationService validationService,
            ProductImageStorageService productImageStorageService,
            ProductImageMetaDataService imageMetaDataService
    ) {
        this.productRepository = productRepository;
        this.validationService = validationService;
        this.productImageStorageService = productImageStorageService;
        this.imageMetaDataService = imageMetaDataService;
    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer productId) {
        return this
                .productRepository
                .findById(productId)
                .orElseThrow( () -> new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Transactional
    public Product createProduct(Product productToBeCreated) {
        if(this.validationService.isProductValid(productToBeCreated)) {
            TimestampUtils.setTimestampsToNow(productToBeCreated);
            var createdProduct = this.productRepository.save(productToBeCreated);
            log.info("Created new products" + createdProduct);
            return createdProduct;
        }
        throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT);
    }

    @Transactional
    public Product updateProductById(Integer productId, Product productToBeUpdated) {
        if(productToBeUpdated.getProductId().equals(productId)){
            LocalDateTime storedCreatedAtTimestamp = this.findProductById(productId).getCreatedAt();
            TimestampUtils.updateTimeStamps(productToBeUpdated, storedCreatedAtTimestamp);
            Product updatedProduct = this.productRepository.save(productToBeUpdated);
            log.info("Updated product: " + updatedProduct);
            return updatedProduct;
        }
        throw new ValueMismatchException(ErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Transactional
    public void deleteProductById(Integer productId) {
        if(this.productRepository.existsById(productId)) {
            this.productRepository.deleteById(productId);
            log.info("Deleted product with id: " +  productId);
            return;
        }
        throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    @Transactional
    public ProductImageMetaData uploadProductImageByProductId(
            Integer productId,
            MultipartFile imageToBeUploaded,
            @Nullable ProductImageMetaData imageMetaData) {
        if (!this.productRepository.existsById(productId)) {
            throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
        }
        ProductImageMetaData uploadedProductImage = this
                .productImageStorageService
                .storeProductImage(productId, imageToBeUploaded, imageMetaData);
        return uploadedProductImage;
    }

    @Transactional(readOnly = true)
    public List<ProductImageMetaData> getProductImagesMetadataById(Integer productId) {
        return this.findProductById(productId).getImageList();
    }

    public DoubleNameFileCarrier downloadMainProductImageByProductId(Integer productId) {
        var productToBeInspected = this.findProductById(productId);
        if(this.hasMainImage(productToBeInspected)) {
            ProductImageMetaData mainImage = this.obtainMainImage(productToBeInspected);
            String originaImageName = mainImage.getOriginalFileName();
            MediaType imageMediaType = MediaType.parseMediaType(mainImage.getMimeType());
            Resource obtainedImageResource = this.productImageStorageService.obtainProductImage(mainImage);
            return new DoubleNameFileCarrier(obtainedImageResource, originaImageName, imageMediaType);
        }
        throw new NoDataFoundException(ErrorMessages.NO_MAIN_ELEMENT_WAS_FOUND);
    }

    @Transactional(readOnly = true)
    public DoubleNameFileCarrier downloadProductImageByProductId(Integer productId, Integer productImageId) {
        if(this.productRepository.existsById(productId)) {
            ProductImageMetaData productImageMetaData = this.imageMetaDataService.findImageMetaDataById(productImageId);
            Resource obtainedImageResource = this.productImageStorageService.obtainProductImage(productImageMetaData);
            String originalFileName = productImageMetaData.getOriginalFileName();
            MediaType imageMediaType = MediaType.parseMediaType(productImageMetaData.getMimeType());
            return new DoubleNameFileCarrier(obtainedImageResource, originalFileName, imageMediaType);
        }
        throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    public boolean hasMainImage(Product productToBeInspected) {
        return productToBeInspected
                .getImageList()
                .parallelStream()
                .anyMatch((image) -> image.isMain());
    }

    public ProductImageMetaData obtainMainImage(Product productToBeInspected) {
        return productToBeInspected
                .getImageList()
                .parallelStream()
                .filter( image -> image.isMain())
                .findAny()
                .orElseThrow( () -> new NoDataFoundException(ErrorMessages.NO_MAIN_ELEMENT_WAS_FOUND));
    }

    public void deleteProductImageByProduct(Integer productId, Integer productImageId) {
        if(this.productRepository.existsById(productId)) {
            ProductImageMetaData productImageToBeDeleted = this.imageMetaDataService.findImageMetaDataById(productImageId);
            this.productImageStorageService.eliminateProductImageCompletely(productImageToBeDeleted);
        }
        throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }
}
