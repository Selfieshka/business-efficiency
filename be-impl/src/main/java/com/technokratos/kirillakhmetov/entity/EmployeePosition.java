package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePosition {

    @EmbeddedId
    private EmployeePositionId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("positionId")
    @JoinColumn(name = "position_id")
    private Position position;
}