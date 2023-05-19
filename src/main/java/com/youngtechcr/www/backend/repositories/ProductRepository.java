package com.youngtechcr.www.backend.repositories;

import com.youngtechcr.www.backend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
