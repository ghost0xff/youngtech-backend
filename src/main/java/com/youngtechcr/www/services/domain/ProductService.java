package com.youngtechcr.www.services.domain;

import com.youngtechcr.www.domain.Brand;
import com.youngtechcr.www.domain.Category;
import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.Subcategory;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.exceptions.custom.ValueMismatchException;
import com.youngtechcr.www.repositories.BrandRepository;
import com.youngtechcr.www.repositories.CategoryRepository;
import com.youngtechcr.www.repositories.ProductRepository;
import com.youngtechcr.www.repositories.SubcategoryRepository;
import com.youngtechcr.www.services.BasicCrudService;
import com.youngtechcr.www.utils.ErrorMessages;
import com.youngtechcr.www.utils.TimestampUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ProductService implements BasicCrudService<Product> {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private SubcategoryRepository subcategoryRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository,
                          SubcategoryRepository subcategoryRepository){
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public Product findById(Integer productId) {
        return this
                .productRepository
                .findById(productId)
                .orElseThrow( () -> new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND));
    }

    @Override
    @Transactional
    public Product create(Product productToBeCreated) {
        if(this.isValid(productToBeCreated)) {
            TimestampUtils.setTimestampsToNow(productToBeCreated);
            var createdProduct = this.productRepository.save(productToBeCreated);
            log.info("Created new products" + createdProduct);
            return createdProduct;
        }
        throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT);
    }

    @Override
    @Transactional
    public Product updateById(Integer productId, Product productToBeUpdated) {
        if(productToBeUpdated.getProductId().equals(productId)){
            LocalDateTime storedCreatedAtTimestamp = this.findById(productId).getCreatedAt();
            TimestampUtils.updateTimeStamps(productToBeUpdated, storedCreatedAtTimestamp);
            Product updatedProduct = this.productRepository.save(productToBeUpdated);
            log.info("Updated product: " + updatedProduct);
            return updatedProduct;
        }
        throw new ValueMismatchException(ErrorMessages.PROVIDED_IDS_DONT_MATCH);
    }

    @Override
    public void deleteById(Integer productId) {
        if(this.productRepository.existsById(productId)) {
            this.productRepository.deleteById(productId);
            log.info("Deleted product with id: " +  productId);
            return;
        }
        throw new NoDataFoundException(ErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND);
    }

    public boolean isValid(Product productToBeValidated) {
        boolean isValid = true;
        Brand brandOfProductToBeCreated = productToBeValidated.getBrand();
        if(brandOfProductToBeCreated == null || !this.brandRepository.existsById(brandOfProductToBeCreated.getBrandId()))
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_BRAND);
        Category categoryOfProductToBeCreated = productToBeValidated.getCategory();
        if(categoryOfProductToBeCreated == null || !this.categoryRepository.existsById(categoryOfProductToBeCreated.getCategoryId()))
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_CATEGORY);
        Subcategory subcategoryOfProductToBeCreated = productToBeValidated.getSubcategory();
        if(subcategoryOfProductToBeCreated == null || !this.subcategoryRepository.existsById(subcategoryOfProductToBeCreated.getSubcategoryId()))
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_SUBCATEGORY);
        if(productToBeValidated.getDiscountPercentage() >= 0 && productToBeValidated.getDiscountPercentage() <= 100) {
            isValid = true;
        } else {
            isValid = false;
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_DISCOUNT_PERCENTAGE);
        }
        if(productToBeValidated.getPrice() <= 0)
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_PRICE);
        if(productToBeValidated.getDescription().length() > 255)
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_DESCRIPTION);
        if(productToBeValidated.getStock() < 0)
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_STOCK);
        if(productToBeValidated.getName().length() > 45)
            throw new InvalidElementException(ErrorMessages.INVALID_PRODUCT_REASON_NAME);
        return isValid;
    }
}
