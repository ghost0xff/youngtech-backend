package com.youngtechcr.www.product;

import com.youngtechcr.www.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findById(Integer productId);
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
}
