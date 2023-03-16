package com.example.ebankingsmalldemo.service;

import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.entities.BankAccount;
import com.example.ebankingsmalldemo.repositories.AccountHolderRepo;
import com.example.ebankingsmalldemo.repositories.BankAccountRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class MappersServiceImpl implements IMappersService {
    private AccountHolderRepo accountHolderRepo;
    private BankAccountRepo bankAccountRepo;
    @Override
    public AccountHolder findAccountHolderById(String id) {
        return accountHolderRepo.findById(id).orElseThrow(() -> new RuntimeException("Account holder not found"));
    }

    @Override
    public BankAccount findBankAccountById(String id) {
        return bankAccountRepo.findById(id).orElseThrow(() -> new RuntimeException("Bank account not found"));
    }
}
