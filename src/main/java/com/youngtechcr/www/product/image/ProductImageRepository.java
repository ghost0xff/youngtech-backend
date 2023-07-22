package com.youngtechcr.www.product.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {


    Optional<ProductImage> findByIsMain(boolean isMain);
    boolean existsByIsMain(boolean isMain);

}
