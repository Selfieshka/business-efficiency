package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
