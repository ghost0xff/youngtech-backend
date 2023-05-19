package com.youngtechcr.www.backend.repositories;

import com.youngtechcr.www.backend.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
