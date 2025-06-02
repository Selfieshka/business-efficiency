package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.entity.Employee;
import com.technokratos.kirillakhmetov.form.EmployeeForm;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    Employee toEmployee(EmployeeForm employeeForm);
}
