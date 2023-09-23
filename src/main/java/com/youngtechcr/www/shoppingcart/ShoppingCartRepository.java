package com.youngtechcr.www.shoppingcart;

import com.youngtechcr.www.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository
        extends JpaRepository<ShoppingCart, Integer> {

        Optional<ShoppingCart> findByCustomer(Customer customer);
}
