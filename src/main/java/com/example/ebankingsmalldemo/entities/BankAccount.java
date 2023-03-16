package com.example.ebankingsmalldemo.entities;

import com.example.ebankingsmalldemo.enums.BankAccountStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class BankAccount {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private BankAccountStatus accountStatus;
    private double accountBalance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private AccountHolder accountHolder;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Transaction> transactions;
}
