package com.youngtechcr.www.backend.repositories;

import com.youngtechcr.www.backend.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
