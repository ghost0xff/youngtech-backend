package com.youngtechcr.www.backend.services;

import com.youngtechcr.www.backend.domain.Invoice;
import com.youngtechcr.www.backend.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.backend.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;


    @Transactional
    public List<Invoice> findAllInvoice() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        if( !invoiceList.isEmpty() ) {
            return invoiceList;
        }
        throw new EmptyRepositoryException("No invoice found in repository");
    }



}
