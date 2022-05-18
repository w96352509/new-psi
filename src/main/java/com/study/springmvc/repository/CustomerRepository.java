package com.study.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.springmvc.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
