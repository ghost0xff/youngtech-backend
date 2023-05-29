package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findById(Integer categoryId);
    boolean existsByName(String name);


}
