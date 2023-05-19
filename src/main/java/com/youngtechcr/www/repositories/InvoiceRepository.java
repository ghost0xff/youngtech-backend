package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
