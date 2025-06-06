package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.EmployeeResponse;
import com.technokratos.kirillakhmetov.form.EmployeeForm;

import java.util.List;

public interface StaffService {
    List<EmployeeResponse> getStaffByOwnerId(Long ownerId);

    void saveEmployee(EmployeeForm employeeForm);

    void deleteEmployeeById(Long employeeId);
}
