package com.youngtechcr.www.backend.repositories;

import com.youngtechcr.www.backend.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
