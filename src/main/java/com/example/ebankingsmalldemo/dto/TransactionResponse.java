package com.example.ebankingsmalldemo.dto;

import com.example.ebankingsmalldemo.enums.TransactionType;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data @ToString
public class TransactionResponse {
    private TransactionType transactionType;
    private double amount;
    private Date transactionDate;
    private String bankAccountReceiver;
    private String bankAccountGiver;
}
