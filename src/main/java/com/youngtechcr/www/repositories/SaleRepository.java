package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
