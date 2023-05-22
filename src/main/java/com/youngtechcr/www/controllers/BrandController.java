package com.youngtechcr.www.controllers;


import com.youngtechcr.www.domain.Brand;
import com.youngtechcr.www.services.domain.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> fetchedBrands = this.brandService.findAllBrands();
        return ResponseEntity.ok(fetchedBrands);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable(name = "id") Integer brandId) {
        Brand fetchedBrand = this.brandService.findById(brandId);
        return ResponseEntity.ok().body(fetchedBrand);
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brandToBeCreated){
        Brand createdBrand = this.brandService.createBrand(brandToBeCreated);
        ResponseEntity<Brand> responseEntity = new ResponseEntity<>(createdBrand,HttpStatus.CREATED);
        return responseEntity;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Brand> deleteBrandById(@Validated @PathVariable(name = "id") Integer brandId){
        this.brandService.deleteBrandById(brandId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrandById(
            @Validated @PathVariable(name = "id") Integer brandId,
            @RequestBody Brand brandToBeUpdated
        ) {
        Brand updatedBrand = this.brandService.updateBrandById(brandId, brandToBeUpdated);
        return ResponseEntity.ok(updatedBrand);
    }


}
