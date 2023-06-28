package com.youngtechcr.www.product.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageMetaDataRepository extends JpaRepository<ProductImageMetaData, Integer> {


    Optional<ProductImageMetaData> findByIsMainImage(boolean isMainImage);
    boolean existsByIsMainImage(boolean isMainImage);

}
