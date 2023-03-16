package com.example.ebankingsmalldemo.dto;

import com.example.ebankingsmalldemo.entities.BankAccount;
import com.example.ebankingsmalldemo.enums.TransactionType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data @Builder @ToString
public class TransactionRequest {
    private Long id;
    private TransactionType transactionType;
    private double amount;
    private Date transactionDate;
    private String bankAccountReceiver;
    private String bankAccountGiver;
    private String bankAccountNumber;
}
