package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.EmployeeDto;
import com.technokratos.kirillakhmetov.entity.Employee;
import com.technokratos.kirillakhmetov.entity.EmployeePosition;
import com.technokratos.kirillakhmetov.entity.EmployeePositionId;
import com.technokratos.kirillakhmetov.entity.Position;
import com.technokratos.kirillakhmetov.form.EmployeeForm;
import com.technokratos.kirillakhmetov.repository.EmployeeRepository;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.repository.PositionRepository;
import com.technokratos.kirillakhmetov.repository.StaffRepository;
import com.technokratos.kirillakhmetov.util.mapper.EmployeeMapper;
import com.technokratos.kirillakhmetov.util.mapper.StaffMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final PositionRepository positionRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final OwnerRepository ownerRepository;
    private final StaffMapper staffMapper;

    public List<EmployeeDto> getStaffByOwnerId(Long ownerId) {
        return staffMapper.toEmployeeDto(staffRepository.findAllByOwnerId(ownerId));
    }

    @Transactional
    public void saveEmployee(EmployeeForm employeeForm) {
        Employee employee = employeeMapper.toEmployee(
                employeeForm,
                ownerRepository.findById(employeeForm.getOwnerId())
                        .orElseThrow(() -> new RuntimeException("User with id = %s - not found".formatted(employeeForm.getOwnerId()))));

        employee.setEmployeePositions(new ArrayList<>());

        for (String employeePosition : employeeForm.getPositions()) {
            System.out.println(employeePosition);
            Position position = positionRepository.findByName(employeePosition)
                    .orElseThrow(() -> new RuntimeException("Position not found: " + employeePosition));

            EmployeePosition newEmployeePosition = new EmployeePosition();
            EmployeePositionId id = new EmployeePositionId();
            id.setEmployeeId(employee.getId());
            id.setPositionId(position.getId());

            newEmployeePosition.setId(id);
            newEmployeePosition.setEmployee(employee);
            newEmployeePosition.setPosition(position);

            employee.getEmployeePositions().add(newEmployeePosition);
            System.out.println(position);
        }

        employeeRepository.save(employee);
    }

    public void deleteEmployeeById(Long employeeId) {
        staffRepository.deleteById(employeeId);
    }
}
