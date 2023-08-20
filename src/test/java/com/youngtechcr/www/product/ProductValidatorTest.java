package com.youngtechcr.www.product;

import com.youngtechcr.www.brand.BrandRepository;
import com.youngtechcr.www.brand.BrandService;
import com.youngtechcr.www.category.CategoryRepository;
import com.youngtechcr.www.category.CategoryService;
import com.youngtechcr.www.category.subcategory.SubcategoryRepository;
import com.youngtechcr.www.category.subcategory.SubcategoryService;
import com.youngtechcr.www.exceptions.custom.InvalidElementException;
import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.validation.ValidationOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductValidatorTest {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private SubcategoryRepository subcategoryRepository;
    private ProductValidator productValidator;
    private RegexService regexService;
    private BrandService brandService;
    private CategoryService categoryService;
    private SubcategoryService subcategoryService;

    @BeforeEach
    void setUp(){
        this.productRepository = mock(ProductRepository.class);
        this.brandRepository = mock(BrandRepository.class);
        this.categoryRepository = mock(CategoryRepository.class);
        this.subcategoryRepository = mock(SubcategoryRepository.class);
        this.brandService = new BrandService(brandRepository);
        this.subcategoryService = new SubcategoryService(subcategoryRepository);
        this.categoryService = new CategoryService(categoryRepository, subcategoryService);
        this.regexService = new RegexService();
        this.productValidator = new ProductValidator(productRepository, brandService, regexService, subcategoryService, categoryService );


        when(brandRepository.existsById(
                ProductValidatorTestUtils.withAllValid().getBrand().getBrandId()
        )).thenReturn(true);
    }

    @Test
    @DisplayName("Invalid product name (too long && too && null) should throw InvalidElementException")
    void given_Invalid_Product_Name_should_Throw_InvalidElementException(){

        // Preparing data
        var withNullishName = ProductValidatorTestUtils
                .withInvalidName(ValidationOption.NULL_OR_DEFAULT);
        var withTooShortName = ProductValidatorTestUtils
                .withInvalidName(ValidationOption.TOO_SHORT);
        var withTooLongName = ProductValidatorTestUtils
                .withInvalidName(ValidationOption.TOO_LONG);

        // Assertions
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withNullishName));
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withTooShortName));
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withTooLongName));
    }

    @Test
    @DisplayName("Valid name should return true")
    void given_Valid_Product_Name_Should_Return_True(){
        // Preparing Data
        var withValidName = ProductValidatorTestUtils
                .withAllValid();
        // Assertions
        assertTrue(productValidator.isValid(withValidName));
    }

    @Test
    @DisplayName("Invalid stock (less than 0) should throw InvalidElementException")
    void given_Invalid_Product_Stock_Should_Throw_InvalidElementException(){
        // Preparing Data
        var withStockTooShort = ProductValidatorTestUtils
                .withInvalidStock(ValidationOption.TOO_SHORT);
        // Assertions
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withStockTooShort));
    }

    @Test
    @DisplayName("Valid stock should return true")
    void given_Valid_Product_Should_Return_True(){
        // Preparing Data
        var withValidStock = ProductValidatorTestUtils
                .withAllValid();
        // Assertions
        assertTrue(productValidator.isValid(withValidStock));
    }

    @Test
    @DisplayName("Invalid description (too long && too short should throw InvalidElementException")
    void given_Invalid_Product_Description_should_Throw_InvalidElementException(){

        // Preparing data
        var withDescriptionTooLong = ProductValidatorTestUtils
                .withInvalidDescription(ValidationOption.TOO_SHORT);
        var withDescriptionTooShort = ProductValidatorTestUtils
                .withInvalidDescription(ValidationOption.TOO_LONG);
        var withNullishDescription = ProductValidatorTestUtils
                .withInvalidDescription(ValidationOption.NULL_OR_DEFAULT);

        // Assertions
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withDescriptionTooShort));
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withDescriptionTooLong));
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withNullishDescription));
    }

    @Test
    @DisplayName("Valid description should return true")
    void given_Product_With_Valid_Description_Should_Return_True() {
        // Preparing Data
        var withValidDescription = ProductValidatorTestUtils.withAllValid();
        // Assertions
        assertTrue(productValidator.isValid(withValidDescription));
    }

    @Test
    @DisplayName("Invalid price (too low) should throw InvalidElementException")
    void given_Invalid_Product_Price_Should_Throw_InvalidElementException() {
        // Preparing Data
        var withPriceTooLow = ProductValidatorTestUtils
                .withInvalidPrice(ValidationOption.TOO_SHORT);
        // Assertions
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withPriceTooLow));
    }

    @Test
    @DisplayName("Price totally valid should should return true")
    void given_Valid_Product_Price_Should_Return_True() {
        // Prepating Data
        var withValidPrice = ProductValidatorTestUtils.withAllValid();
        //Assertions
        assertTrue(productValidator.isValid(withValidPrice));
    }


    @Test
    @DisplayName("Discount percentage below 0 or equal should should throw" +
            "InvalidElementException")
    void given_Invalid_Product_Discpount_Percentage_Should_Throw_InvalidElementException(){

        // Preparing data
        Product withDiscountBelowZero =
                ProductValidatorTestUtils.withInvalidDiscount(ValidationOption.TOO_SHORT);
        Product withDiscountEqualsZero = ProductValidatorTestUtils
                .withInvalidDiscount(ValidationOption.EMPTY);

        // Assertions
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withDiscountBelowZero));
        assertThrows(InvalidElementException.class,
                () -> productValidator.isValid(withDiscountEqualsZero));
    }

    @Test
    @DisplayName("Discount percentage totally valid should should return true")
    void given_Valid_Product_Discount_Percentage_Should_Return_True() {
        // Prepating Data
        var withValidPrice = ProductValidatorTestUtils.withAllValid();
        //Assertions
        assertTrue(productValidator.isValid(withValidPrice));
    }

    @Test
    @DisplayName("Discount percentage totally valid should should return true")
    void given_Valid_Product_Brand_Should_Return_True() {
        // Prepating Data
        var withExistingBrand = ProductValidatorTestUtils.withAllValid();
        //Assertions
        assertTrue(productValidator.isValid(withExistingBrand));
    }

//    @Test
//    @DisplayName("Test product validation")
//    void givenValidProduct_ShouldReturnTrue(){
//        when(this.brandRepository.existsById(1)).thenReturn(true);
//        when(this.categoryRepository.existsById(1)).thenReturn(true);
//        when(this.subcategoryRepository.existsById(1)).thenReturn(true);
//
//        Brand brandToBeTested = new Brand(1);
//        brandToBeTested.setName("Corsair");
//
//        Category categoryToBeTested = new Category(1);
//        categoryToBeTested.setName("Peripherals");
//
//        Subcategory subcategoryToBeTested = new Subcategory(1);
//        subcategoryToBeTested.setName("Keyboards");
//
//        Product productToBeValidates = new Product();
//        productToBeValidates.setProductId(1);
//        productToBeValidates.setName("000000000000000000000000000000000000000000000");
//        productToBeValidates.setStock(1);
//        productToBeValidates.setDescription("0000000000000000000000000000000000000" +
//                "000000000000000000000000000000000000000000000000000000000000000" +
//                "00000000000000000000000000000000000000000000000000000000000000000" +
//                "00000000000000000000000000000000000000000000000000000000000000" +
//                "0000000000000000000000000000");
//        productToBeValidates.setPrice(1f);
//        productToBeValidates.setDiscountPercentage(0.0f);
//        productToBeValidates.setBrand(brandToBeTested);
//        productToBeValidates.setCategory(categoryToBeTested);
//        productToBeValidates.setSubcategory(subcategoryToBeTested);
//
//        assertTrue(this.productValidator.isValid(productToBeValidates));
//
//
//    }


}