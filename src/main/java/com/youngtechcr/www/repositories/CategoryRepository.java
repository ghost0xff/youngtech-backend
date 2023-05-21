package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
