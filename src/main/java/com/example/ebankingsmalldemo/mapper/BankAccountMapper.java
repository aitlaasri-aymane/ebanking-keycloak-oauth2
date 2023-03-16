package com.example.ebankingsmalldemo.mapper;

import com.example.ebankingsmalldemo.dto.BankAccountRequest;
import com.example.ebankingsmalldemo.dto.BankAccountResponse;
import com.example.ebankingsmalldemo.entities.BankAccount;
import com.example.ebankingsmalldemo.service.IMappersService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IMappersService.class})
public interface BankAccountMapper {

    @Mapping(target = "accountHolder.id", source = "accountHolderId")
    BankAccount toBankAccount(BankAccountRequest bankAccountRequest);
    @Mapping(target = "accountNumber", source = "accountNumber")
    BankAccount fromResponsetoBankAccount(BankAccountResponse bankAccountResponse);
    @Mapping(target = "accountNumber", source = "accountNumber")
    BankAccountResponse toBankAccountResponse(BankAccount bankAccount);
    @Mapping(target = "accountHolderId", source = "accountHolder.id")
    BankAccountRequest toBankAccountRequest(BankAccount bankAccount);
}
