package com.example.ebankingsmalldemo;

import com.example.ebankingsmalldemo.dto.AccountHolderRequest;
import com.example.ebankingsmalldemo.dto.AccountHolderResponse;
import com.example.ebankingsmalldemo.dto.BankAccountRequest;
import com.example.ebankingsmalldemo.dto.BankAccountResponse;
import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.entities.BankAccount;
import com.example.ebankingsmalldemo.enums.BankAccountStatus;
import com.example.ebankingsmalldemo.mapper.AccountHolderMapper;
import com.example.ebankingsmalldemo.mapper.BankAccountMapper;
import com.example.ebankingsmalldemo.service.IBankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@Slf4j
public class EbankingSmallDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingSmallDemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    CommandLineRunner runner(IBankService BankService, AccountHolderMapper accountMapper) {
        return args -> {
            AccountHolderRequest accountHolderRequest = AccountHolderRequest.builder()
                    .id("60413e15-0527-4aee-bd6a-5bd0361448e7")
                    .fullName("John Doe")
                    .email("Johndoe@gmail.com")
                    .build();
            AccountHolder accountHolder = accountMapper.fromResponsetoAccountHolder(BankService.createAccountHolder(accountHolderRequest));
            BankAccountRequest bankAccount1Request = BankAccountRequest.builder()
                    .accountBalance(1000)
                    .accountStatus(BankAccountStatus.ACTIVE)
                    .accountHolderId(accountHolder.getId())
                    .build();
            BankAccountRequest bankAccount2Request = BankAccountRequest.builder()
                    .accountBalance(5000)
                    .accountStatus(BankAccountStatus.ACTIVE)
                    .accountHolderId(accountHolder.getId())
                    .build();
            BankAccountRequest bankAccount3Request = BankAccountRequest.builder()
                    .accountBalance(5000000)
                    .accountStatus(BankAccountStatus.SUSPENDED)
                    .accountHolderId(accountHolder.getId())
                    .build();
            BankAccountResponse bankAccount1 = BankService.createBankAccount(bankAccount1Request);
            BankAccountResponse bankAccount2 = BankService.createBankAccount(bankAccount2Request);
            BankAccountResponse bankAccount3 = BankService.createBankAccount(bankAccount3Request);

            BankService.deposit(bankAccount1.getAccountNumber(), 1000);
            BankService.withdraw(bankAccount2.getAccountNumber(), 5000);
            BankService.transfer(bankAccount1.getAccountNumber(),bankAccount2.getAccountNumber(), 1000);

            log.info(bankAccount1Request.toString());
            log.info(bankAccount2Request.toString());
            log.info(bankAccount3Request.toString());
        };
    }

}
