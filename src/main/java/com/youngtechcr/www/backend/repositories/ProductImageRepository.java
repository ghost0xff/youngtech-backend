package com.youngtechcr.www.backend.repositories;

import com.youngtechcr.www.backend.domain.Product;
import com.youngtechcr.www.backend.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
