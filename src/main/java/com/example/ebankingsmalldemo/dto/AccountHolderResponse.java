package com.example.ebankingsmalldemo.dto;

import com.example.ebankingsmalldemo.entities.BankAccount;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data @ToString
public class AccountHolderResponse {
    public String id;
    public String fullName;
    public String email;
    public List<BankAccountResponse> bankAccounts;
}
