package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
