package com.youngtechcr.www.validation;

import com.youngtechcr.www.brand.Brand;
import com.youngtechcr.www.category.Category;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.subcategory.Subcategory;
import com.youngtechcr.www.productimage.ProductImageMetaData;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.brand.BrandRepository;
import com.youngtechcr.www.category.CategoryRepository;
import com.youngtechcr.www.product.ProductRepository;
import com.youngtechcr.www.subcategory.SubcategoryRepository;
import com.youngtechcr.www.exceptions.ErrorMessages;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValidationService {

    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private SubcategoryRepository subcategoryRepository;

    public ValidationService(ProductRepository productRepository,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository,
                          SubcategoryRepository subcategoryRepository){
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }


    @Transactional(readOnly = true)
    public boolean isProductValid(Product productToBeValidated) {
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

    public boolean isProductImageValid(ProductImageMetaData productImageFileData) {
        boolean isValid = true;

        return isValid;
    }


}
