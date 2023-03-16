package com.example.ebankingsmalldemo.dto;

import com.example.ebankingsmalldemo.entities.BankAccount;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data @Builder @ToString
public class AccountHolderRequest {
    public String id;
    public String fullName;
    public String email;
}
