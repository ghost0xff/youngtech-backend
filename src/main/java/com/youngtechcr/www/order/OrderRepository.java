package com.youngtechcr.www.order;

import com.youngtechcr.www.customer.Customer;
import com.youngtechcr.www.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Integer> {


}
