package com.eCommerce.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eCommerce.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
