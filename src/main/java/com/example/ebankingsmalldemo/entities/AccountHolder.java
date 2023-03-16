package com.example.ebankingsmalldemo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class AccountHolder {
    @Id
    public String id;
    public String fullName;
    public String email;
    @OneToMany(mappedBy = "accountHolder", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<BankAccount> bankAccounts;
}
