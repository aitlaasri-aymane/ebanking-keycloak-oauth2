package com.example.ebankingsmalldemo.dto;

import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.entities.Transaction;
import com.example.ebankingsmalldemo.enums.BankAccountStatus;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data @ToString
public class BankAccountResponse {
    private String accountNumber;
    private BankAccountStatus accountStatus;
    private double accountBalance;
    private List<TransactionResponse> transactions;
}
