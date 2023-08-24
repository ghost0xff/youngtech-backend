package com.youngtechcr.www.customer;

import com.youngtechcr.www.security.user.User;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer createCustomerFromUser(User user) {
//        Customer customerToBePersisted = new
        return null;
    }
}
