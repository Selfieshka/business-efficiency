package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePositionId implements Serializable {
    private Long employeeId;
    private Long positionId;
}
