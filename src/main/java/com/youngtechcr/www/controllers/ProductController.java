package com.youngtechcr.www.controllers;


import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import com.youngtechcr.www.services.storage.ProductImageFileDataStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {


    @Autowired
    private ProductImageFileDataStorageService productImageStorageService;

    @PostMapping(path = "/{id}/images/")
    public ResponseEntity<?> uploadProductImageByProduct(
            @Validated @PathVariable(name = "id") Integer productId,
            @Validated @RequestPart(name = "product-meta-data") Product product,
            @Validated @RequestPart(name = "product-image") MultipartFile uploadedImage
    ){
        productImageStorageService.processUpload(productId, uploadedImage);
        return null;
    }

    @PostMapping(path = "")
    public ResponseEntity<?> uploadProductImageByProductImageFileData(){
        return null;
    }


}
