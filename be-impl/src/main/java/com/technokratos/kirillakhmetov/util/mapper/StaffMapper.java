package com.technokratos.kirillakhmetov.util.mapper;

import com.technokratos.kirillakhmetov.dto.EmployeeDto;
import com.technokratos.kirillakhmetov.entity.Employee;
import com.technokratos.kirillakhmetov.entity.EmployeePosition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffMapper {
    @Mapping(source = "employeePositions", target = "position", qualifiedByName = "extractPositionNamesToList")
    EmployeeDto toEmployeeDto(Employee employee);

    List<EmployeeDto> toEmployeeDto(List<Employee> employees);

    @Named("extractPositionNamesToList")
    default List<String> extractPositionNamesToList(List<EmployeePosition> positions) {
        return positions.stream()
                .map(ep -> ep.getPosition().getName())
                .toList();
    }
}
