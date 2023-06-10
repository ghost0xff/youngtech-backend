package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.storage.ProductImageFileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageFileDataRepository extends JpaRepository<ProductImageFileMetaData, Integer> {


    Optional<ProductImageFileMetaData> findByIsMainImage(boolean isMainImage);
    boolean existsByIsMainImage(boolean isMainImage);

}
