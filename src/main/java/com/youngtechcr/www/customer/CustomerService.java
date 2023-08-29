package com.youngtechcr.www.customer;

import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }
}
