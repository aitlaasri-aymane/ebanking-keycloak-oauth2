package com.example.ebankingsmalldemo.dto;

import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.entities.Transaction;
import com.example.ebankingsmalldemo.enums.BankAccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data @Builder @ToString
public class BankAccountRequest {
    private String accountNumber;
    private BankAccountStatus accountStatus;
    private double accountBalance;
    private String accountHolderId;
}
