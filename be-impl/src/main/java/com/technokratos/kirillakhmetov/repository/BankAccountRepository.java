package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query("""
            SELECT SUM(ba.amount)
            FROM BankAccount ba
            WHERE ba.owner.id = :ownerId
            """)
    Optional<Double> sumAllAmountByOwnerId(@Param("ownerId") Long ownerId);
}
