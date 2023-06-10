package com.youngtechcr.www.controllers;


import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.DoubleNameFileCarrier;
import com.youngtechcr.www.domain.storage.ProductImageMetaData;
import com.youngtechcr.www.services.ProductService;
import com.youngtechcr.www.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable("id") Integer productId) {
        Product fetchedProduct = this.productService.findProductById(productId);
        return ResponseEntity.ok().body(fetchedProduct);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@RequestBody Product productToBeCreated) {
        Product createdProduct = this.productService.createProduct(productToBeCreated);
        return ResponseEntityUtils.created(createdProduct);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProductById(
            @PathVariable("id") Integer productId,
            @RequestBody Product productToBeUpdated
    ){
        Product updatedProduct = this.productService.updateProductById(productId, productToBeUpdated);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") Integer productId) {
        this.productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/images")
    public ResponseEntity<ProductImageMetaData> uploadImageByProductId(
            @PathVariable("id") Integer productId,
            @RequestPart(name = "file-data") MultipartFile imageToBeUploaded,
            @RequestPart(name = "meta-data", required = false) ProductImageMetaData imageMetaData
    ) {
        var uploadedProductImage = this.productService.uploadProductImageByProductId(productId, imageToBeUploaded, imageMetaData);
        return ResponseEntityUtils.created(uploadedProductImage);
    }

    @GetMapping(path = "/{id}/images")
    public ResponseEntity<?> downloadMainImage(
            @PathVariable("id") Integer productId,
            @RequestParam(name = "main", required = false) boolean isMain
    ) {
        if(isMain) {
            DoubleNameFileCarrier resourceWithSomeMetaData = this.productService.downloadMainProductImageByProductId(productId);
            return ResponseEntityUtils.downloadedFileWithCustomName(resourceWithSomeMetaData.resource(), resourceWithSomeMetaData.customFileName(),resourceWithSomeMetaData.fileMediaType() );
        }
        List<ProductImageMetaData> imagesMetaData = this.productService.getProductImagesMetadataById(productId);
        return ResponseEntity.ok().body(imagesMetaData);
    }


}
