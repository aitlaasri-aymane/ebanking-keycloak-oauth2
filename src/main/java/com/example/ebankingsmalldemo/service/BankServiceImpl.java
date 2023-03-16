package com.example.ebankingsmalldemo.service;

import com.example.ebankingsmalldemo.dto.*;
import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.entities.BankAccount;
import com.example.ebankingsmalldemo.entities.Transaction;
import com.example.ebankingsmalldemo.enums.BankAccountStatus;
import com.example.ebankingsmalldemo.enums.TransactionType;
import com.example.ebankingsmalldemo.mapper.AccountHolderMapper;
import com.example.ebankingsmalldemo.mapper.BankAccountMapper;
import com.example.ebankingsmalldemo.mapper.TransactionMapper;
import com.example.ebankingsmalldemo.repositories.AccountHolderRepo;
import com.example.ebankingsmalldemo.repositories.BankAccountRepo;
import com.example.ebankingsmalldemo.repositories.TransactionsRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service @Transactional @Slf4j
public class BankServiceImpl implements IBankService {

    private AccountHolderRepo accountHolderRepo;
    private BankAccountRepo bankAccountRepo;
    private TransactionsRepo transactionRepo;
    private AccountHolderMapper accountHolderMapper;
    private BankAccountMapper bankAccountMapper;
    private TransactionMapper transactionMapper;

    @Override
    public AccountHolderResponse createAccountHolder(AccountHolderRequest accountHolderRequest) {
        AccountHolder accountHolder = accountHolderMapper.toAccountHolder(accountHolderRequest);
        AccountHolder savedAccountHolder = accountHolderRepo.save(accountHolder);
        return accountHolderMapper.toAccountHolderResponse(savedAccountHolder);
    }

    @Override
    public BankAccountResponse createBankAccount(BankAccountRequest bankAccountRequest) {
        BankAccount bankAccount = bankAccountMapper.toBankAccount(bankAccountRequest);
        BankAccount savedBankAccount = bankAccountRepo.save(bankAccount);
        return bankAccountMapper.toBankAccountResponse(savedBankAccount);
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = transactionMapper.toTransaction(transactionRequest);
        Transaction savedTransaction = transactionRepo.save(transaction);
        return transactionMapper.toTransactionResponse(savedTransaction);
    }

    @Override
    public BankAccountResponse deposit(String accId, double amount) {
        if (amount <= 0 || amount > 1000000.01) {
            throw new RuntimeException("Invalid amount");
        }
        BankAccount bankAccount = bankAccountRepo.findById(accId).orElseThrow(() -> new RuntimeException("Bank account not found"));
        BankAccountRequest bankAccountRequest = bankAccountMapper.toBankAccountRequest(bankAccount);
        if (bankAccountRequest.getAccountStatus() != BankAccountStatus.ACTIVE) {
            throw new RuntimeException("Account is suspended");
        }
        bankAccountRequest.setAccountBalance(bankAccountRequest.getAccountBalance() + (int)amount);
        BankAccount updatedBankAccount = bankAccountMapper.toBankAccount(bankAccountRequest);
        BankAccount savedBankAccount = bankAccountRepo.save(updatedBankAccount);
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .transactionType(TransactionType.DEPOSIT)
                .amount((int)amount)
                .transactionDate(new Date())
                .bankAccountNumber(savedBankAccount.getAccountNumber())
                .build();
        createTransaction(transactionRequest);
        return getBankAccountById(savedBankAccount.getAccountNumber());
    }

    @Override
    public BankAccountResponse withdraw(String accId, double amount) {
        if (amount < 0.0 || amount > 100000.01) {
            throw new RuntimeException("Invalid amount");
        }
        BankAccount bankAccount = bankAccountRepo.findById(accId).orElseThrow(() -> new RuntimeException("Bank account not found"));
        BankAccountRequest bankAccountRequest = bankAccountMapper.toBankAccountRequest(bankAccount);
        if (bankAccountRequest.getAccountStatus() != BankAccountStatus.ACTIVE) {
            throw new RuntimeException("Account is suspended");
        }
        double Balance = bankAccountRequest.getAccountBalance();
        if (Balance < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        bankAccountRequest.setAccountBalance(Balance - (int)amount);
        BankAccount updatedBankAccount = bankAccountMapper.toBankAccount(bankAccountRequest);
        BankAccount savedBankAccount = bankAccountRepo.save(updatedBankAccount);
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .transactionType(TransactionType.WITHDRAW)
                .amount((int)amount)
                .transactionDate(new Date())
                .bankAccountNumber(savedBankAccount.getAccountNumber())
                .build();
        createTransaction(transactionRequest);
        return bankAccountMapper.toBankAccountResponse(savedBankAccount);
    }

    @Override
    public BankAccountResponse transfer(String acc1, String acc2, double amount) {
        if (amount < 0.0 || amount >= 100000.0) {
            throw new RuntimeException("Invalid amount");
        }
        BankAccount bankAccountGiver = bankAccountRepo.findById(acc1).orElseThrow(() -> new RuntimeException("Giving Bank account not found"));
        BankAccount bankAccountReceiver = bankAccountRepo.findById(acc2).orElseThrow(() -> new RuntimeException("Receiving Bank account not found"));
        BankAccountRequest bankAccountGiverRequest = bankAccountMapper.toBankAccountRequest(bankAccountGiver);
        BankAccountRequest bankAccountReceiverRequest = bankAccountMapper.toBankAccountRequest(bankAccountReceiver);
        if (bankAccountGiverRequest.getAccountNumber() == bankAccountReceiverRequest.getAccountNumber()) {
            throw new RuntimeException("Can't transfer to the same account");
        }
        if (bankAccountGiverRequest.getAccountStatus() != BankAccountStatus.ACTIVE || bankAccountReceiverRequest.getAccountStatus() != BankAccountStatus.ACTIVE) {
            throw new RuntimeException("One of the accounts is suspended");
        }
        double Balance = bankAccountGiverRequest.getAccountBalance();
        if (Balance < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        bankAccountGiverRequest.setAccountBalance(Balance - amount);
        bankAccountReceiverRequest.setAccountBalance(bankAccountReceiver.getAccountBalance() + amount);
        BankAccount updatedBankAccountGiver = bankAccountMapper.toBankAccount(bankAccountGiverRequest);
        BankAccount updatedBankAccountReceiver = bankAccountMapper.toBankAccount(bankAccountReceiverRequest);
        BankAccount savedBankAccountGiver = bankAccountRepo.save(updatedBankAccountGiver);
        BankAccount savedBankAccountReceiver = bankAccountRepo.save(updatedBankAccountReceiver);

        TransactionRequest giverTransactionRequest = TransactionRequest.builder()
                .transactionType(TransactionType.TRANSFER)
                .amount(amount)
                .transactionDate(new Date())
                .bankAccountGiver(savedBankAccountGiver.getAccountNumber())
                .bankAccountReceiver(savedBankAccountReceiver.getAccountNumber())
                .bankAccountNumber(savedBankAccountGiver.getAccountNumber())
                .build();
        TransactionRequest receiverTransactionRequest = TransactionRequest.builder()
                .transactionType(TransactionType.TRANSFER)
                .amount(amount)
                .transactionDate(new Date())
                .bankAccountGiver(savedBankAccountGiver.getAccountNumber())
                .bankAccountReceiver(savedBankAccountReceiver.getAccountNumber())
                .bankAccountNumber(savedBankAccountReceiver.getAccountNumber())
                .build();
        createTransaction(giverTransactionRequest);
        createTransaction(receiverTransactionRequest);
        return bankAccountMapper.toBankAccountResponse(savedBankAccountGiver);
    }

    @Override
    public BankAccountResponse getBankAccountById(String id) {
        BankAccount bankAccount = bankAccountRepo.findById(id).orElseThrow(() -> new RuntimeException("Bank account not found"));
        return bankAccountMapper.toBankAccountResponse(bankAccount);
    }

    @Override
    public AccountHolderResponse getAccountHolderById(String id) {
        AccountHolder accountHolder = accountHolderRepo.findById(id).orElseThrow(() -> new RuntimeException("Account holder not found"));
        AccountHolderResponse accountHolderResponse = accountHolderMapper.toAccountHolderResponse(accountHolder);
        return accountHolderResponse;
    }

    @Override
    public BankAccountResponse suspendBankAccount(String id) {
        BankAccount bankAccount = bankAccountRepo.findById(id).orElseThrow(() -> new RuntimeException("Bank account not found"));
        BankAccountRequest bankAccountRequest = bankAccountMapper.toBankAccountRequest(bankAccount);
        bankAccountRequest.setAccountStatus(BankAccountStatus.SUSPENDED);
        BankAccount updatedBankAccount = bankAccountMapper.toBankAccount(bankAccountRequest);
        BankAccount savedBankAccount = bankAccountRepo.save(updatedBankAccount);
        return bankAccountMapper.toBankAccountResponse(savedBankAccount);
    }

    @Override
    public BankAccountResponse activateBankAccount(String id) {
        BankAccount bankAccount = bankAccountRepo.findById(id).orElseThrow(() -> new RuntimeException("Bank account not found"));
        BankAccountRequest bankAccountRequest = bankAccountMapper.toBankAccountRequest(bankAccount);
        bankAccountRequest.setAccountStatus(BankAccountStatus.ACTIVE);
        BankAccount updatedBankAccount = bankAccountMapper.toBankAccount(bankAccountRequest);
        BankAccount savedBankAccount = bankAccountRepo.save(updatedBankAccount);
        return bankAccountMapper.toBankAccountResponse(savedBankAccount);
    }

    @Override
    public List<AccountHolderResponse> getAllAccountHolders() {
        List<AccountHolder> accountHolders = accountHolderRepo.findAll();
        List<AccountHolderResponse> accountHolderResponses = accountHolders.stream().map(accountHolderMapper::toAccountHolderResponse).toList();
        return accountHolderResponses;
    }
}
