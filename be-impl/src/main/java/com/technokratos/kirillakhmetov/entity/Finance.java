package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "finance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Finance {

    @Id
    @Column(name = "finance_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "finance_sequence")
    @SequenceGenerator(name = "finance_sequence", sequenceName = "finance_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "owner_id",
            foreignKey = @ForeignKey(name = "owner_id_fk")
    )
    private Owner owner;

    @Column(name = "type", length = 30, nullable = false)
    private String type;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "category", length = 30, nullable = false)
    private String category;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}