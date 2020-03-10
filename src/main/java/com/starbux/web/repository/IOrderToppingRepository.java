package com.starbux.web.repository;

import com.starbux.entity.OrderTopping;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderToppingRepository extends JpaRepository<OrderTopping, Long> {
}
