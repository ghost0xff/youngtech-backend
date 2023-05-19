package com.youngtechcr.www.services;

import com.youngtechcr.www.domain.Order;
import com.youngtechcr.www.exceptions.custom.EmptyRepositoryException;
import com.youngtechcr.www.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;


    @Transactional
    public List<Order> findAllInvoice() {
        List<Order> invoiceList = orderRepository.findAll();
        if( !invoiceList.isEmpty() ) {
            return invoiceList;
        }
        throw new EmptyRepositoryException("No invoice found in repository");
    }



}
