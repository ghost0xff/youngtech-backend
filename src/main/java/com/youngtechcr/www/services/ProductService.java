package com.youngtechcr.www.services;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private ProductRepository productRepository;
    private ValidationService validationService;
    private ProductImageStorageService productImageStorageService;

    public ProductService(
            ProductRepository productRepository,
            ValidationService validationService,
            ProductImageStorageService productImageStorageService
    ) {
        this.productRepository = productRepository;
        this.validationService = validationService;
        this.productImageStorageService = productImageStorageService;
    }

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
    public ProductImageFileData uploadProductImageByProductId(Integer productId, MultipartFile imageToBeUploaded, @Nullable ProductImageFileData imageMetadata) {
        if (!this.productRepository.existsById(productId)) {
            throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
        }
        ProductImageFileData uploadedProductImage = this
                .productImageStorageService
                .storeProductImage(productId, imageToBeUploaded, imageMetadata);
        return uploadedProductImage;
    }
}
