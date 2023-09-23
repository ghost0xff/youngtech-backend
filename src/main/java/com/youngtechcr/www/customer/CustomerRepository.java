package com.youngtechcr.www.customer;

import com.youngtechcr.www.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUser(User user);
}
