package com.youngtechcr.www.controllers;


import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.services.domain.ProductService;
import com.youngtechcr.www.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController implements BasicCrudController<Product> {

    @Autowired
    private ProductService productService;

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer productId) {
        Product fetchedProduct = this.productService.findById(productId);
        return ResponseEntity.ok().body(fetchedProduct);
    }

    @Override
    public ResponseEntity<Product> create(Product productToBeCreated) {
        Product createdProduct = this.productService.create(productToBeCreated);
        return ResponseEntityUtils.created(createdProduct);
    }

    @Override
    public ResponseEntity<Product> updateById(Integer id, Product toBeUpdated) {
        return null;
    }

    @Override
    public ResponseEntity<Product> deleteById(Integer id) {
        return null;
    }
}
