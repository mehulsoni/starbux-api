package com.starbux.web.repository;

import com.starbux.entity.Topping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IToppingRepository extends JpaRepository<Topping, Long> {
}
