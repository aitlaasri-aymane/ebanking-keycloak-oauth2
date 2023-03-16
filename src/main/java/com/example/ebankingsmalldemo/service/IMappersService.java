package com.example.ebankingsmalldemo.service;

import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.entities.BankAccount;

public interface IMappersService {
    AccountHolder findAccountHolderById(String id);
    BankAccount findBankAccountById(String id);
}
