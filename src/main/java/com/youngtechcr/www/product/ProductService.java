package com.youngtechcr.www.product;
import com.youngtechcr.www.brand.Brand;
import com.youngtechcr.www.brand.BrandService;
import com.youngtechcr.www.exceptions.custom.*;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.domain.TimestampedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ProductValidator productValidator;
    private final BrandService brandService;

    public ProductService(
            ProductRepository productRepository,
            ProductValidator productValidator,
            BrandService brandService) {
        this.productRepository = productRepository;
        this.productValidator = productValidator;
        this.brandService = brandService;
    }

   public Page<Product> findSomeProducts(int pageNum, int pageSize) {
        if (pageSize > 1_000) {
            throw new QuantityOfElementsException(
                    HttpErrorMessages.REQUESTED_TOO_MUCH_ENTITIES
            );
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Product> someProducts = productRepository.findAll(pageable);
        return someProducts;
   }

    @Transactional(readOnly = true)
    public Product findById(Integer productId) {
        return this
                .productRepository
                .findById(productId)
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages.
                        NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Transactional(readOnly = true)
    public Product findByName(String name) {
        return this
                .productRepository
                .findByName(name)
                .orElseThrow( () -> new NoDataFoundException(HttpErrorMessages.
                        NO_ELEMENT_WITH_THE_REQUESTED_NAME_WAS_FOUND));
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Product createProduct(Product productToBeCreated) {
        Integer productId = productToBeCreated.getId();
        if (productId != null) {
            throw new InvalidElementException(
                    HttpErrorMessages.CANT_PROVIDE_ID_DURING_ELEMENT_CREATION
            );
        }
        if(this.productValidator.isValid(productToBeCreated, false)) {
            TimestampedUtils.setTimestampsToNow(productToBeCreated);
            var createdProduct = this.productRepository.save(productToBeCreated);
            log.info("Created new product" + createdProduct);
            return createdProduct;
        }
        throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Product updateProductById(Integer productId, Product productToBeUpdated) {
        if(productId.equals(productToBeUpdated.getId()) ){
            Product existingProduct = findById(productId);
            Product updatedProduct = null;
            if(productValidator.isValid(productToBeUpdated, true)) {
                LocalDateTime currentCreatedAt = existingProduct.getCreatedAt();
                TimestampedUtils.updateTimeStamps(productToBeUpdated, currentCreatedAt);
                updatedProduct = this.productRepository.save(productToBeUpdated);
                log.info("Updated product: " + updatedProduct);
                return updatedProduct;
            }
        } throw new ValueMismatchException(HttpErrorMessages
                .PROVIDED_IDS_DONT_MATCH); }
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void deleteProductById(Integer productId) {
        Product toBeDeleted = findById(productId);
        productRepository.deleteById(toBeDeleted.getId());
        log.info("Deleted product: " + toBeDeleted);
        return;
    }

    @Transactional(readOnly = true)
    public Product findProductByBrandId(Integer productId, Integer brandId) {
        Brand requestedBrand = brandService.findById(brandId);
        Product requestedProduct = findById(productId);
        if(requestedProduct.getBrand().equals(requestedBrand)) {
            return requestedProduct; } throw new ValueMismatchException(HttpErrorMessages .REQUESTED_CHILD_ELEMENT_DOESNT_EXIST); }

    @Transactional(readOnly = true)
    public boolean existsProductById(Integer productId) {
        return this.productRepository
                .existsById(productId);
    }

    public boolean hasMainImage(Product product) {
        return product
                .getImages()
                .stream()
                .anyMatch((image) -> image.main());
    }

}
