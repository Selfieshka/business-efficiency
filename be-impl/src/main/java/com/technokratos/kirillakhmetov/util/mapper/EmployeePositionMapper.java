package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.entity.Employee;
import com.technokratos.kirillakhmetov.entity.EmployeePosition;
import com.technokratos.kirillakhmetov.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeePositionMapper {

    @Mapping(target = "id.employeeId", source = "employee.id")
    @Mapping(target = "id.positionId", source = "position.id")
    @Mapping(target = "employee", source = "employee")
    @Mapping(target = "position", source = "position")
    EmployeePosition toEmployeePosition(Employee employee, Position position);
}