package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "position")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_sequence")
    @SequenceGenerator(name = "position_sequence", sequenceName = "position_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 15, unique = true)
    private String name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeePosition> employeePositions;
}