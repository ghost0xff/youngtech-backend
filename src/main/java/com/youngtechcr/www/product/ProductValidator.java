package com.youngtechcr.www.product;

import com.youngtechcr.www.brand.Brand;
import com.youngtechcr.www.brand.BrandService;
import com.youngtechcr.www.category.Category;
import com.youngtechcr.www.category.CategoryService;
import com.youngtechcr.www.category.subcategory.SubcategoryService;
import com.youngtechcr.www.domain.Validator;
import com.youngtechcr.www.category.subcategory.Subcategory;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.order.OrderedProduct;
import com.youngtechcr.www.product.image.ProductImage;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.regex.Regexes;
import com.youngtechcr.www.sale.Sale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductValidator implements Validator<Product> {

    private final ProductRepository productRepository;
    private final BrandService brandService;
    private final SubcategoryService subcategoryService;
    private final RegexService regexService;
    private final CategoryService categoryService;

    public ProductValidator(ProductRepository productRepository, BrandService brandService,
                            RegexService regexService,
                            SubcategoryService subcategoryService,
                            CategoryService categoryService){
        this.productRepository = productRepository;
        this.brandService = brandService;
        this.subcategoryService = subcategoryService;
        this.regexService = regexService;
        this.categoryService = categoryService;
    }

    @Override
    public boolean isValid(Product product, boolean isUpdate) throws InvalidElementException {
        Integer productId = product.getProductId();
        String name = product.getName();
        int stock = product.getStock();
        String description = product.getDescription();
        float price = product.getPrice();
        float  discount = product.getDiscountPercentage();
        Brand brand = product.getBrand();
        Category category = product.getCategory();
        Subcategory subcategory = product.getSubcategory();
        List<ProductImage> imageList = product.getImageList();
        List<Sale> salesList = product.getSaleList();
        List<OrderedProduct> orderedProductsList = product.getOrderedProductsList();

        if(isUpdate) {
           validateExistingId(productId);
        }
        return
                isNameValid(name)
                && isStockValid(stock)
                && isDesciptionValid(description)
                && isPriceValid(price)
                && isDiscountPercentageValid(discount)
                && isBrandValid(brand)
                && isCategoryValid(category)
                && isSubcategoryValid(subcategory)
                ;
    }
    @Transactional(readOnly = true)
    private boolean validateExistingId(Integer productId) {
        if(productId != null && productRepository.existsById(productId)) {
           return true;
        } throw new InvalidElementException(
                HttpErrorMessages.NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND
        );
    }

    @Transactional(readOnly = true)
    private boolean isNameValid(String name) {
        if(productRepository.existsByName(name)) {
            throw new AlreadyExistsException(
                    HttpErrorMessages.CANT_CREATE_DUPLICATE_NAME
            );
        }
        if(name != null && regexService.matches(Regexes.PRODUCT_NAME_PATTERN, name) ) {
            return true;
        } throw new InvalidElementException(
                HttpErrorMessages.INVALID_PRODUCT_REASON_NAME);
    }
    private boolean isStockValid(int stock) {
        if(stock >= 0) {
            return true;
        } throw new InvalidElementException(
                HttpErrorMessages.INVALID_PRODUCT_REASON_STOCK);
    }

    private boolean isDesciptionValid(String description) {
        if (description != null && regexService.matches(
                Regexes.PRODUCT_DESCRIPTION_PATTERN, description)) {
            return true;
        } throw new InvalidElementException(
                HttpErrorMessages.INVALID_PRODUCT_REASON_DESCRIPTION);
    }


    private boolean isPriceValid(float price) {
        if (price >= 0) {
            return true;
        } throw new InvalidElementException(
                HttpErrorMessages.INVALID_PRODUCT_REASON_PRICE);
    }

    private boolean isDiscountPercentageValid(float discount) {
        if(discount >= 0 && discount <= 100) { // must be between 1 - 99
           return true;
        } throw new InvalidElementException(
                HttpErrorMessages.INVALID_PRODUCT_REASON_DISCOUNT_PERCENTAGE);
    }

    @Transactional(readOnly = true)
    private boolean isBrandValid(Brand brand) {
        if(brand != null && brandService.existsById(brand.getBrandId())) {
            return true;
        } throw new InvalidElementException(
                HttpErrorMessages.INVALID_PRODUCT_REASON_BRAND);
    }

    @Transactional(readOnly = true)
    private boolean isCategoryValid(Category category) {
        if(category != null && categoryService.existsById(category.getCategoryId())) {
            return true;
        } throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_CATEGORY);
    }

    @Transactional(readOnly = true)
    private boolean isSubcategoryValid(Subcategory subcategory) {
        if(subcategory != null && subcategoryService.existsById(subcategory.getSubcategoryId())) {
            return true;
        } throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_SUBCATEGORY);
    }


    //    @Transactional(readOnly = true) //    public boolean isValid(Product productToBeValidated) { //        boolean isValid = true; //        Brand brandOfProductToBeCreated = productToBeValidated.getBrand(); //        if(brandOfProductToBeCreated == null || !this.brandRepository.existsById(brandOfProductToBeCreated.getBrandId()))
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_BRAND);
//        Category categoryOfProductToBeCreated = productToBeValidated.getCategory();
//        if(categoryOfProductToBeCreated == null || !this.categoryRepository.existsById(categoryOfProductToBeCreated.getCategoryId()))
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_CATEGORY);
//        Subcategory subcategoryOfProductToBeCreated = productToBeValidated.getSubcategory();
//        if(subcategoryOfProductToBeCreated == null || !this.subcategoryRepository.existsById(subcategoryOfProductToBeCreated.getSubcategoryId()))
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_SUBCATEGORY);
//        if(productToBeValidated.getDiscountPercentage() >= 0 && productToBeValidated.getDiscountPercentage() <= 100) {
//            isValid = true;
//        } else {
//            isValid = false;
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_DISCOUNT_PERCENTAGE);
//        }
//        if(productToBeValidated.getPrice() <= 0)
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_PRICE);
//        if(productToBeValidated.getDescription().length() > 255)
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_DESCRIPTION);
//        if(productToBeValidated.getStock() < 0)
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_STOCK);
//        if(productToBeValidated.getName().length() > 45)
//            throw new InvalidElementException(HttpErrorMessages.INVALID_PRODUCT_REASON_NAME);
//        return isValid;
//    }

}
