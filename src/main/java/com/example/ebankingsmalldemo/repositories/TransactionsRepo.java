package com.example.ebankingsmalldemo.repositories;

import com.example.ebankingsmalldemo.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepo extends JpaRepository<Transaction, Long> {
}
