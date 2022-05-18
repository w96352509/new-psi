package com.study.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.springmvc.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
