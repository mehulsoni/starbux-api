package com.starbux.web.repository;

import com.starbux.entity.OrderProduct;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
