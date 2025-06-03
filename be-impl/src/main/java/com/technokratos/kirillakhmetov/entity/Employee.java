package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "owner_id",
            foreignKey = @ForeignKey(name = "owner_id_fk")
    )
    private Owner owner;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(name = "patronymic", length = 30)
    private String patronymic;

    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeePosition> employeePositions;

    public void addPosition(Position position) {
        EmployeePosition employeePosition = new EmployeePosition(
                new EmployeePositionId(this.id, position.getId()), this, position);
        employeePositions.add(employeePosition);
        position.getEmployeePositions().add(employeePosition);
    }
}