package com.youngtechcr.www.controllers;


import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import com.youngtechcr.www.services.storage.ProductImageFileDataStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {


    @Autowired
    private ProductImageFileDataStorageService productImageStorageService;

    @PostMapping(path = "/image")
    public ResponseEntity<?> uploadProductImageByProduct(
            @Validated @RequestPart(name = "product-meta-data") ProductImageFileData selectedProductImage,
            @Validated @RequestPart(name = "product-image") MultipartFile uploadedImage
    ){

        return null;
    }

    @PostMapping(path = "")
    public ResponseEntity<?> uploadProductImageByProductImageFileData(){
        return null;
    }


}
