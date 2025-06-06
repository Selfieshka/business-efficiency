package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.EmployeeResponse;
import com.technokratos.kirillakhmetov.form.EmployeeForm;

import java.util.List;

public interface StaffService {
    List<EmployeeResponse> getStaffByOwnerId(Long ownerId);

    void saveEmployee(Long ownerId, EmployeeForm employeeForm);

    void deleteEmployeeById(Long ownerId, Long employeeId);
}
