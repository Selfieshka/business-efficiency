package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.entity.Employee;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.form.EmployeeForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner")
    @Mapping(source = "employeeForm.firstName", target = "firstName")
    @Mapping(source = "employeeForm.lastName", target = "lastName")
    @Mapping(source = "employeeForm.patronymic", target = "patronymic")
    Employee toEmployee(
            EmployeeForm employeeForm,
            Owner owner);
}
