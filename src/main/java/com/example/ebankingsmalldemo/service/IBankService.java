package com.example.ebankingsmalldemo.service;

import com.example.ebankingsmalldemo.dto.*;
import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.entities.BankAccount;

import java.util.List;

public interface IBankService {
    AccountHolderResponse createAccountHolder(AccountHolderRequest accountHolderRequest);
    BankAccountResponse createBankAccount(BankAccountRequest bankAccountRequest);
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
    BankAccountResponse deposit(String accId, double amount);
    BankAccountResponse withdraw(String accId, double amount);
    BankAccountResponse transfer(String acc1, String acc2, double amount);
    BankAccountResponse getBankAccountById(String id);
    AccountHolderResponse getAccountHolderById(String id);
    BankAccountResponse suspendBankAccount(String id);
    BankAccountResponse activateBankAccount(String id);
    List<AccountHolderResponse> getAllAccountHolders();
}
