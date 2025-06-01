package com.technokratos.kirillakhmetov.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "bank_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_sequence")
    @SequenceGenerator(name = "bank_account_sequence", sequenceName = "bank_account_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "owner_id",
            referencedColumnName = "owner_id",
            foreignKey = @ForeignKey(name = "owner_id_fk")
    )
    private Owner owner;

    @Column(name = "bank_name", length = 20, nullable = false)
    private String bankName;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;
}