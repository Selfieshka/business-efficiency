package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "invoice_id",
            referencedColumnName = "invoice_id",
            foreignKey = @ForeignKey(name = "invoice_id_fk")
    )
    private Invoice invoice;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "measurement_unit", length = 10, nullable = false)
    private String measurementUnit;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;
}