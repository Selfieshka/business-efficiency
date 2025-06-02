package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.entity.Employee;
import com.technokratos.kirillakhmetov.form.EmployeeForm;
import com.technokratos.kirillakhmetov.repository.StaffRepository;
import com.technokratos.kirillakhmetov.util.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final EmployeeMapper employeeMapper;

    public List<Employee> getStaffByOwnerId(Long id) {
        return staffRepository.findAllByOwnerId(id);
    }

    public void saveEmployee(EmployeeForm employeeForm) {
        staffRepository.save(employeeMapper.toEmployee(employeeForm));
    }

    public void deleteEmployeeById(Long employeeId) {
        staffRepository.deleteById(employeeId);
    }
}
