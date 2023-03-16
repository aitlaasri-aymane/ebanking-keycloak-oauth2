package com.example.ebankingsmalldemo.mapper;

import com.example.ebankingsmalldemo.dto.AccountHolderRequest;
import com.example.ebankingsmalldemo.dto.AccountHolderResponse;
import com.example.ebankingsmalldemo.entities.AccountHolder;
import com.example.ebankingsmalldemo.service.IBankService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountHolderMapper {
    @Mapping(source = "fullName", target = "fullName")
    AccountHolder toAccountHolder(AccountHolderRequest accountHolderRequest);
    @Mapping(target = "fullName", source = "fullName")
    AccountHolderResponse toAccountHolderResponse(AccountHolder accountHolder);
    @Mapping(target = "fullName", source = "fullName")
    AccountHolder fromResponsetoAccountHolder(AccountHolderResponse accountHolderResponse);
}
