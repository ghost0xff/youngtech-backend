package com.youngtechcr.www.product.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductImageTest {

    private static final Logger log = LoggerFactory.getLogger(ProductImageTest.class);

    @Test
    @DisplayName("Verify recently implemented builder pattern in entity class")
    void builder_Pattern_Instantiation_Should_Not_Have_Any_Weird_Behaviour() {
        assertDoesNotThrow( () -> {
                ProductImage image = ProductImage
                        .builder()
                        .originalName("dfasa fedjksakldm tt.txt")
                        .serverName("server_name_1231321.txt")
                        .build();
                log.info("Created product image: " + image);
        });
    }

}