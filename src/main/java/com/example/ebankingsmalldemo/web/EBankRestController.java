package com.example.ebankingsmalldemo.web;

import com.example.ebankingsmalldemo.dto.*;
import com.example.ebankingsmalldemo.service.IBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EBankRestController {
    @Autowired
    private IBankService bankService;

    @GetMapping("/BankAccount/{id}")
    public BankAccountResponse getBankAccounts(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return bankService.getBankAccountById(id);
    }

    @GetMapping("/AccountHolder/{id}")
    public AccountHolderResponse getAccountHolderById(@PathVariable String id) {
        return bankService.getAccountHolderById(id);
    }

    @GetMapping("/AccountHolder")
    public AccountHolderResponse getAccountHolder(@AuthenticationPrincipal Jwt jwt) {
        return bankService.getAccountHolderById(jwt.getSubject());
    }

    @PostMapping("/AccountHolder")
    @PreAuthorize("hasRole('ADMIN')")
    public AccountHolderResponse createAccountHolder(@RequestBody AccountHolderRequest accountHolderRequest) {
        return bankService.createAccountHolder(accountHolderRequest);
    }

    @PostMapping("/BankAccount")
    public BankAccountResponse createBankAccount(@RequestBody BankAccountRequest bankAccountRequest) {
        return bankService.createBankAccount(bankAccountRequest);
    }

    @PostMapping("/BankAccount/{id}/deposit")
    public BankAccountResponse deposit(@RequestParam double amount, @PathVariable String id) {
        return bankService.deposit(id, amount);
    }

    @PostMapping("/BankAccount/{id}/withdraw")
    public BankAccountResponse withdraw(@RequestParam double amount, @PathVariable String id) {
        return bankService.withdraw(id, amount);
    }

    @PostMapping("/BankAccount/{id1}/transfer/{id2}")
    public BankAccountResponse transfer(@RequestParam double amount, @PathVariable String id1, @PathVariable String id2) {
        return bankService.transfer(id1, id2, amount);
    }

    @PutMapping("/BankAccount/{id}/suspend")
    public BankAccountResponse suspendBankAccount(@PathVariable String id) {
        return bankService.suspendBankAccount(id);
    }

    @PutMapping("/BankAccount/{id}/activate")
    public BankAccountResponse activateBankAccount(@PathVariable String id) {
        return bankService.activateBankAccount(id);
    }

    @GetMapping("/AccountHolders")
    public List<AccountHolderResponse> getAllAccountHolders() {
        return bankService.getAllAccountHolders();
    }
}
