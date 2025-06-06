package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_sequence")
    @SequenceGenerator(name = "invoice_sequence", sequenceName = "invoice_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "owner_id",
            foreignKey = @ForeignKey(name = "owner_id_fk")
    )
    private Owner owner;

    @Column(name = "number", length = 30, nullable = false)
    private String number;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "invoice", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Product> products;
}