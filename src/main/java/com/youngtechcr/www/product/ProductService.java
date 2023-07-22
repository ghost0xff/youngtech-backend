package com.youngtechcr.www.product;

import com.youngtechcr.www.brand.Brand;
import com.youngtechcr.www.brand.BrandService;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.product.image.ProductImageService;
import com.youngtechcr.www.product.image.ProductImageStorageService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.domain.TimestampedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductValidator validationService;
    private final BrandService brandService;

    public ProductService(
            ProductRepository productRepository,
            ProductValidator validationService,
            BrandService brandService) {
        this.productRepository = productRepository;
        this.validationService = validationService;
        this.brandService = brandService;
    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer productId) {
        return this
                .productRepository
                .findById(productId)
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages.
                        NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
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
            LocalDateTime storedCreatedAtTimestamp =
                    this.findProductById(productId).getCreatedAt();
            TimestampedUtils.updateTimeStamps(productToBeUpdated, storedCreatedAtTimestamp);
            Product updatedProduct = this.productRepository.save(productToBeUpdated);
            log.info("Updated product: " + updatedProduct);
            return updatedProduct;
        }
        throw new ValueMismatchException(HttpErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void deleteProductById(Integer productId) {
        Product toBeDeleted = findProductById(productId);
        productRepository.deleteById(toBeDeleted.getProductId());
        log.info("Deleted product: " + toBeDeleted);
        return;
    }

    @Transactional(readOnly = true)
    public Product findProductByBrandId(Integer productId, Integer brandId) {
        Brand requestedBrand = brandService.findById(brandId);
        Product requestedProduct = findProductById(productId);
        if(requestedProduct.getBrand().equals(requestedBrand)) {
            return requestedProduct;
        }
        throw new ValueMismatchException(HttpErrorMessages
                .REQUESTED_CHILD_ELEMENT_DOESNT_EXIST);
    }

    @Transactional(readOnly = true)
    public boolean existsProductById(Integer productId) {
        return this.productRepository
                .existsById(productId);
    }

    public boolean hasMainImage(Product product) {
        return product
                .getImageList()
                .stream()
                .anyMatch((image) -> image.isMain());
    }

}
