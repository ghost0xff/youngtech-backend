package com.youngtechcr.www.controllers;


import com.youngtechcr.www.services.storage.ProductImageStorageService;
import com.youngtechcr.www.utils.LocalFileSystemPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {

    @Autowired
    private ProductImageStorageService productImageStorageService;

    @PostMapping
        public ResponseEntity<?> uploadProductFile(@Validated @RequestPart(name = "product-image") MultipartFile multipartFile) {
        this.productImageStorageService.processAndSave(multipartFile);
        return ResponseEntity.ok(multipartFile.getOriginalFilename());
    }

}
