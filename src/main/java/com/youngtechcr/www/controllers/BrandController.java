package com.youngtechcr.www.controllers;

import com.youngtechcr.www.domain.Brand;
import com.youngtechcr.www.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/brands")
public class BrandController {


    @Autowired
    private BrandService brandService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Brand>> listAllBrands() {
        List<Brand> brandList = brandService.findAllBrands();
        return ResponseEntity.ok().body(brandList);
    }

    @PostMapping
    public void createBrand(@RequestBody @Validated Brand brand) {
        brandService.saveBrand(brand);
    }

    @PutMapping
    public ResponseEntity updateBrand(@RequestBody @Validated Brand brand) {
        brandService.saveBrand(brand);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity deleteBrand(@RequestBody Brand brand) {
        brandService.deleteBrand(brand);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        return responseEntity;
    }

}
