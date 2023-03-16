package com.example.ebankingsmalldemo.repositories;

import com.example.ebankingsmalldemo.entities.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepo extends JpaRepository<AccountHolder, String> {
}
