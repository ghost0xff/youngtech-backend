package com.youngtechcr.www.category.subcategory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    boolean existsByName(String name);

}
