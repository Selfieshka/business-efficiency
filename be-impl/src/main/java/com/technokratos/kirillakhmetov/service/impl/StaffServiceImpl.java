package com.technokratos.kirillakhmetov.service.impl;

import com.technokratos.kirillakhmetov.dto.response.EmployeeResponse;
import com.technokratos.kirillakhmetov.entity.Employee;
import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.entity.Position;
import com.technokratos.kirillakhmetov.form.EmployeeForm;
import com.technokratos.kirillakhmetov.repository.EmployeeRepository;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.repository.PositionRepository;
import com.technokratos.kirillakhmetov.repository.StaffRepository;
import com.technokratos.kirillakhmetov.service.StaffService;
import com.technokratos.kirillakhmetov.util.mapper.EmployeeMapper;
import com.technokratos.kirillakhmetov.util.mapper.EmployeePositionMapper;
import com.technokratos.kirillakhmetov.util.mapper.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;
    private final OwnerRepository ownerRepository;
    private final EmployeeMapper employeeMapper;
    private final StaffMapper staffMapper;
    private final EmployeePositionMapper employeePositionMapper;

    @Override
    public List<EmployeeResponse> getStaffByOwnerId(Long ownerId) {
        return staffMapper.toResponse(staffRepository.findAllByOwnerId(ownerId));
    }

    @Override
    @Transactional
    public void saveEmployee(Long ownerId, EmployeeForm employeeForm) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User with id = %s - not found".formatted(ownerId)));

        Employee employee = employeeMapper.toEmployee(employeeForm, owner);

        Set<String> positionNames = new HashSet<>(employeeForm.positions());

        List<Position> positions = positionRepository.findAllByNameIn(positionNames);
        Map<String, Position> positionMap = positions.stream()
                .collect(Collectors.toMap(Position::getName, Function.identity()));

        if (positionMap.size() < positionNames.size()) {
            Set<String> missing = new HashSet<>(positionNames);
            missing.removeAll(positionMap.keySet());
            throw new RuntimeException("Позиция не была найдена: " + missing);
        }

        employeeForm.positions().forEach(name -> employee.getEmployeePositions()
                .add(employeePositionMapper.toEmployeePosition(employee, positionMap.get(name)))
        );

        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        staffRepository.deleteById(employeeId);
    }
}
