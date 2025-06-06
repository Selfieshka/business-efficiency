package com.technokratos.kirillakhmetov.service.impl;

import com.technokratos.kirillakhmetov.dto.response.ApiFinanceResponse;
import com.technokratos.kirillakhmetov.form.BankAccountForm;
import com.technokratos.kirillakhmetov.repository.BankAccountRepository;
import com.technokratos.kirillakhmetov.repository.OwnerRepository;
import com.technokratos.kirillakhmetov.service.BankAccountService;
import com.technokratos.kirillakhmetov.util.mapper.BankAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final OwnerRepository ownerRepository;

    @Override
    public void saveBankAccount(long ownerId, BankAccountForm bankAccountForm) {
        bankAccountRepository.save(
                bankAccountMapper.toBankAccount(
                        bankAccountForm,
                        ownerRepository.findById(ownerId)
                                .orElseThrow(() -> new RuntimeException("User with id = %s - not found".formatted(ownerId)))
                ));
    }

    @Override
    public ApiFinanceResponse calculateAllAmount(Long ownerId) {
        return new ApiFinanceResponse(bankAccountRepository.sumAllAmountByOwnerId(ownerId).orElse(0D));
    }
}
