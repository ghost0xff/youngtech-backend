package com.youngtechcr.www.brand;

import com.youngtechcr.www.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    List<Brand> findAll();
    Optional<Brand> findById(Integer brandId);
    Optional<Brand> findByName(String name);
    boolean existsByName(String name);



}
