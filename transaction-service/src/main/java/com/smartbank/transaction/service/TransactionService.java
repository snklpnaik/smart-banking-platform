package com.smartbank.transaction.service;


import java.util.List;

import com.smartbank.transaction.dto.DepositRequest;
import com.smartbank.transaction.dto.TransferRequest;
import com.smartbank.transaction.dto.WithdrawRequest;
import com.smartbank.transaction.entity.Transaction;

public interface TransactionService {
	Transaction deposit(DepositRequest request);
	
	Transaction withdraw(WithdrawRequest request);
	
	Transaction transfer(TransferRequest request);
	
	List<Transaction> getTransactionHistory(String accountNumber);
}
