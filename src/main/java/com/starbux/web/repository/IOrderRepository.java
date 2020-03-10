package com.starbux.web.repository;

import com.starbux.entity.OrderCart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<OrderCart, Long> {


}
