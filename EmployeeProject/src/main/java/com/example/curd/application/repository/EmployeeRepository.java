package com.example.curd.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.curd.application.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
