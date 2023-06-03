package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.storage.ProductImageFileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageFileDataRepository extends JpaRepository<ProductImageFileData, Integer> {


    Optional<ProductImageFileData> findByIsMainImage(boolean isMainImage);
    boolean existsByIsMainImage(boolean isMainImage);

}
