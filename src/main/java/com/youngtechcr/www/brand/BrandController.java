package com.youngtechcr.www.brand;


import com.youngtechcr.www.http.BasicCrudController;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.http.ResponseEntityUtils;
import com.youngtechcr.www.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/brands")
public class BrandController implements BasicCrudController<Brand> {


    private final BrandService brandService;
    private final ProductService productService;

    public BrandController(BrandService brandService, ProductService productService) {
        this.brandService = brandService;
        this.productService = productService;
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<Brand> findById(@PathVariable("id") Integer brandId) {
        Brand fetchedBrand = this.brandService.findById(brandId);
        return ResponseEntity.ok().body(fetchedBrand);
    }
    @Override
    @PostMapping
    public ResponseEntity<Brand> create(@RequestBody Brand brandToBeCreated){
        Brand createdBrand = this.brandService.create(brandToBeCreated);
        return ResponseEntityUtils.created(createdBrand);
    }

    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<Brand> updateById(
            @Validated @PathVariable("id") Integer brandId,
            @RequestBody Brand brandToBeUpdated
    ) {
        Brand updatedBrand = this.brandService.updateById(brandId, brandToBeUpdated);
        return ResponseEntity.ok(updatedBrand);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Brand> deleteById(@Validated @PathVariable("id") Integer brandId){
        this.brandService.deleteById(brandId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{brandId}/products")
    public ResponseEntity<List<Product>> findProductsByBrand(@PathVariable Integer brandId) {
       List<Product> fetchedProducts = this.brandService.findAllProductsByBrandId(brandId);
       return ResponseEntity.ok().body(fetchedProducts);
    }

    @GetMapping(path = "/{brandId}/products/{productId}")
    public ResponseEntity<Product> findProductByBrand(
            @PathVariable Integer brandId,
            @PathVariable Integer productId
    ) {
        Product retrievedProduct = this.productService.findProductByBrandId(productId, brandId);
        return ResponseEntity.ok().body(retrievedProduct);
    }

}
