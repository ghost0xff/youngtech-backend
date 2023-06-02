package com.youngtechcr.www.services.domain;

import com.youngtechcr.www.domain.Brand;
import com.youngtechcr.www.domain.Category;
import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.Subcategory;
import com.youngtechcr.www.repositories.BrandRepository;
import com.youngtechcr.www.repositories.CategoryRepository;
import com.youngtechcr.www.repositories.ProductRepository;
import com.youngtechcr.www.repositories.SubcategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private SubcategoryRepository subcategoryRepository;
    private ProductService productService;

    @BeforeEach
    void setUp(){
        this.productRepository = mock(ProductRepository.class);
        this.brandRepository = mock(BrandRepository.class);
        this.categoryRepository = mock(CategoryRepository.class);
        this.subcategoryRepository = mock(SubcategoryRepository.class);
        this.productService = new ProductService(productRepository, brandRepository, categoryRepository, subcategoryRepository);
    }
    @Test
    @DisplayName("Test product validation")
    void givenValidProduct_ShouldReturnTrue(){
        when(this.brandRepository.existsById(1)).thenReturn(true);
        when(this.categoryRepository.existsById(1)).thenReturn(true);
        when(this.subcategoryRepository.existsById(1)).thenReturn(true);

        Brand brandToBeTested = new Brand(1);
        brandToBeTested.setName("Corsair");

        Category categoryToBeTested = new Category(1);
        categoryToBeTested.setName("Peripherals");

        Subcategory subcategoryToBeTested = new Subcategory(1);
        subcategoryToBeTested.setName("Keyboards");

        Product productToBeValidates = new Product();
        productToBeValidates.setProductId(1);
        productToBeValidates.setName("000000000000000000000000000000000000000000000");
        productToBeValidates.setStock(1);
        productToBeValidates.setDescription("0000000000000000000000000000000000000" +
                "000000000000000000000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000000" +
                "00000000000000000000000000000000000000000000000000000000000000" +
                "0000000000000000000000000000");
        productToBeValidates.setPrice(1f);
        productToBeValidates.setDiscountPercentage(0.0f);
        productToBeValidates.setBrand(brandToBeTested);
        productToBeValidates.setCategory(categoryToBeTested);
        productToBeValidates.setSubcategory(subcategoryToBeTested);

        assertTrue(this.productService.isValid(productToBeValidates));

    }


}