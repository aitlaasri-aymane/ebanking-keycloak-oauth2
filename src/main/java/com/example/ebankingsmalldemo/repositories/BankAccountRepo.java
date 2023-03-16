package com.example.ebankingsmalldemo.repositories;

import com.example.ebankingsmalldemo.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, String> {
}
