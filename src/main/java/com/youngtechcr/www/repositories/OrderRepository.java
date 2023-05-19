package com.youngtechcr.www.repositories;

import com.youngtechcr.www.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {
}
