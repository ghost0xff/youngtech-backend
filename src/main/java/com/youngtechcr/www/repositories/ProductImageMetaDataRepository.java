package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.storage.ProductImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageMetaDataRepository extends JpaRepository<ProductImageMetaData, Integer> {


    Optional<ProductImageMetaData> findByIsMainImage(boolean isMainImage);
    boolean existsByIsMainImage(boolean isMainImage);

}
