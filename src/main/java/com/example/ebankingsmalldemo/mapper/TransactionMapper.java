package com.example.ebankingsmalldemo.mapper;

import com.example.ebankingsmalldemo.dto.TransactionRequest;
import com.example.ebankingsmalldemo.dto.TransactionResponse;
import com.example.ebankingsmalldemo.entities.Transaction;
import com.example.ebankingsmalldemo.service.IMappersService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IMappersService.class})
public interface TransactionMapper {
    @Mapping(target = "bankAccount", source = "bankAccountNumber")
    Transaction toTransaction(TransactionRequest transactionRequest);
    @Mapping(target = "amount", source = "amount")
    TransactionResponse toTransactionResponse(Transaction transaction);
}
