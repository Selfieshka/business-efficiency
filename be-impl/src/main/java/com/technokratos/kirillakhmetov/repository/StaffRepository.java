package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByOwnerId(Long id);
}
