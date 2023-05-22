package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.storage.ProductImageFileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageFileDataRepository extends JpaRepository<ProductImageFileData, Integer> {
}
