package com.youngtechcr.www.controllers;


import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import com.youngtechcr.www.services.domain.ProductService;
import com.youngtechcr.www.utils.ResponseEntityUtils;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") Integer productId) {
        Product fetchedProduct = this.productService.findById(productId);
        return ResponseEntity.ok().body(fetchedProduct);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Product productToBeCreated) {
        Product createdProduct = this.productService.create(productToBeCreated);
        return ResponseEntityUtils.created(createdProduct);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProductById(
            @PathVariable("id") Integer productId,
            @RequestBody Product productToBeUpdated
    ){
        Product updatedProduct = this.productService.updateById(productId, productToBeUpdated);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") Integer productId) {
        this.productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/images")
    public ResponseEntity<List<ProductImageFileData>> uploadImageByProductId(
            @PathVariable("id") Integer productId,
            @RequestPart MultipartFile imageToBeUploaded
    ) {
        return null;
    }

}
