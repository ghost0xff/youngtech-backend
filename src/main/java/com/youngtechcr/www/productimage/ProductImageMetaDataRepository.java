package com.youngtechcr.www.productimage;

import com.youngtechcr.www.productimage.ProductImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageMetaDataRepository extends JpaRepository<ProductImageMetaData, Integer> {


    Optional<ProductImageMetaData> findByIsMainImage(boolean isMainImage);
    boolean existsByIsMainImage(boolean isMainImage);

}
