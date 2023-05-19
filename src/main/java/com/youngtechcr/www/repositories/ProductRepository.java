package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
