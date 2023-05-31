package com.youngtechcr.www.services.domain;

import com.youngtechcr.www.domain.Brand;
import com.youngtechcr.www.domain.Category;
import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.Subcategory;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.repositories.BrandRepository;
import com.youngtechcr.www.repositories.CategoryRepository;
import com.youngtechcr.www.repositories.ProductRepository;
import com.youngtechcr.www.repositories.SubcategoryRepository;
import com.youngtechcr.www.services.BasicCrudService;
import com.youngtechcr.www.utils.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService implements BasicCrudService<Product> {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;


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
        if(this.productRepository.existsById(productToBeCreated.getProductId()))
            throw new AlreadyExistsException(ErrorMessages.CANT_CREATE_DUPLICATE_ID);

        return null;
    }

    @Override
    public Product updateById(Integer id, Product toBeUpdated) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    private boolean isValid(Product productToBeValidated) {
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
