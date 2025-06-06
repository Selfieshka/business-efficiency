package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.response.ApiFinanceResponse;
import com.technokratos.kirillakhmetov.form.BankAccountForm;

public interface BankAccountService {
    void saveBankAccount(long ownerId, BankAccountForm bankAccountForm);

    ApiFinanceResponse calculateAllAmount(Long ownerId);
}
