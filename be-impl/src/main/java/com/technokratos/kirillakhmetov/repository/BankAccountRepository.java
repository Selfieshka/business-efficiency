package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    double sumAllAmountByOwnerId(Long ownerId);
}
