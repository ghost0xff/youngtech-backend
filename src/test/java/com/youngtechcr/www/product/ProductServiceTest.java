package com.youngtechcr.www.product;


import com.youngtechcr.www.product.image.ProductImageMetaData;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.product.image.ProductImageStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class ProductServiceTest {


    private static final Logger log = LoggerFactory.getLogger(ProductServiceTest.class);

    private ProductService productService;
    private ProductRepository productRepository;
    private ValidationService validationService;
    private ProductImageStorageService productImageStorageService;
    @BeforeEach
    void setUp(){
        this.productRepository = mock(ProductRepository.class);
        this.validationService = mock(ValidationService.class);
        this.productService = new ProductService(
                productRepository,
                validationService,
                productImageStorageService
        );
    }


    @Test
    @DisplayName("test if has main image")
    void given_Product_With_MainImage_Should_Return_True() {
        boolean hasMainImage = this.productService.hasMainImage(DomainUtils.PRODUCT_WITH_IMAGES_AND_MAIN_IMAGE);
        assertTrue(hasMainImage);
    }

    @Test
    @DisplayName("test if has main image")
    void given_Product_With_No_MainImage_Should_Return_False() {
        boolean hasMainImage = this.productService.hasMainImage(DomainUtils.PRODUCT_WITH_IMAGES_AND_NO_MAIN_IMAGE);
        assertFalse(hasMainImage);
    }

    @Test
    @DisplayName("check if can obtain main image from product")
    void given_Product_With_MainImage_Should_Return_MainImage() {
        ProductImageMetaData mainImage = this.productService.obtainMainImage(DomainUtils.PRODUCT_WITH_IMAGES_AND_MAIN_IMAGE);
        log.info("obtained: " + mainImage);
        log.info("expected: " + DomainUtils.MAIN_PRODUCT_IMAGE);
        assertEquals(mainImage, DomainUtils.MAIN_PRODUCT_IMAGE);
    }

    @Test
    @DisplayName("check if can throw exception if no main image was found")
    void given_Product_With_No_MainImage_Should_Throw_NoDataFoundException() {
        assertThrows(NoDataFoundException.class, () -> {
            ProductImageMetaData mainImage = this.productService.obtainMainImage(DomainUtils.PRODUCT_WITH_IMAGES_AND_NO_MAIN_IMAGE);
        });
    }

}
