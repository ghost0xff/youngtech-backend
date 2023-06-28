package com.youngtechcr.www.order;

import com.youngtechcr.www.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {
}
