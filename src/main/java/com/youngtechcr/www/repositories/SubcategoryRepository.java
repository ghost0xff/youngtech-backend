package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    boolean existsByName(String name);

}
