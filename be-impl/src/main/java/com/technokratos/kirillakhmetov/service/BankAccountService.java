package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.FinanceResponse;
import com.technokratos.kirillakhmetov.form.BankAccountForm;

public interface BankAccountService {
    void saveBankAccount(long ownerId, BankAccountForm bankAccountForm);

    FinanceResponse calculateAllAmount(Long ownerId);
}
