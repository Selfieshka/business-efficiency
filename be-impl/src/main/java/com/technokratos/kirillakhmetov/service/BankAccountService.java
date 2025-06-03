package com.technokratos.kirillakhmetov.service;

import com.technokratos.kirillakhmetov.dto.ApiFinanceDto;
import com.technokratos.kirillakhmetov.dto.BankAccountDto;
import com.technokratos.kirillakhmetov.repository.BankAccountRepository;
import com.technokratos.kirillakhmetov.util.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;

    public void addAccount(BankAccountDto bankAccountDto) {
        bankAccountRepository.save(bankAccountMapper.toBankAccount(bankAccountDto));
    }

    public ApiFinanceDto calculateAllAmount(Long ownerId) {
        return new ApiFinanceDto(bankAccountRepository.sumAllAmountByOwnerId(ownerId));
    }
}
