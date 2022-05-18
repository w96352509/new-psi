package com.study.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.springmvc.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
