package com.smartbank.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbank.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findByFromAccountNumberOrToAccountNumber(String fromAccountNumber, String toAccountNumber);
}
