package com.smartbank.transaction.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartbank.transaction.client.AccountClient;
import com.smartbank.transaction.constants.TransactionStatus;
import com.smartbank.transaction.constants.TransactionType;
import com.smartbank.transaction.dto.DepositRequest;
import com.smartbank.transaction.dto.TransferRequest;
import com.smartbank.transaction.dto.UpdateBalanceRequest;
import com.smartbank.transaction.dto.WithdrawRequest;
import com.smartbank.transaction.dto.client.AccountResponse;
import com.smartbank.transaction.entity.Transaction;
import com.smartbank.transaction.repository.TransactionRepository;
import com.smartbank.transaction.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	private final TransactionRepository transactionRepository;
	private final AccountClient accountClient;
	
	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountClient accountClient) {
		this.transactionRepository=transactionRepository;
		this.accountClient=accountClient;
	}

	@Override
	public Transaction deposit(DepositRequest request) {
		
		AccountResponse account = accountClient.getAccountByNumber(request.getAccountNumber());
		if(account==null) {

			return null;
		}
		BigDecimal newBalance = account.getAmount().add(request.getAmount());
		
		UpdateBalanceRequest updateRequest = new UpdateBalanceRequest();
		updateRequest.setAccountNumber(account.getAccountNumber());
		updateRequest.setAmount(newBalance);
		
		accountClient.updateBalance(updateRequest);
		
		Transaction transaction = new Transaction();
		transaction.setFromAccountNumber(null);
		transaction.setToAccountNumber(account.getAccountNumber());
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setTransactionStatus(TransactionStatus.SUCCESS);
		transaction.setCreatedAt(LocalDateTime.now());
		
		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction withdraw(WithdrawRequest request) {
		
		AccountResponse account = accountClient.getAccountByNumber(request.getAccountNumber());
		if(account==null) {

			return null;
		}
		if(account.getAmount().compareTo(request.getAmount()) < 0) { 
			
			throw new RuntimeException("Insufficient Balance");
			
		}
		BigDecimal newBalance = account.getAmount().subtract(request.getAmount());
		
		UpdateBalanceRequest updateRequest = new UpdateBalanceRequest();
		
		updateRequest.setAccountNumber(account.getAccountNumber());
		updateRequest.setAmount(newBalance);
		
		accountClient.updateBalance(updateRequest);
		
		Transaction transaction = new Transaction();
		
		transaction.setFromAccountNumber(account.getAccountNumber());
		transaction.setToAccountNumber(null);
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(TransactionType.WITHDRAW);
		transaction.setTransactionStatus(TransactionStatus.SUCCESS);
		transaction.setCreatedAt(LocalDateTime.now());
		
		return transactionRepository.save(transaction);
	}

	@Override
	public Transaction transfer(Long userId, TransferRequest request) {
		// TODO Auto-generated method stub
		
		if(request.getFromAccountNumber().equals(request.getToAccountNumber())) {
			throw new RuntimeException("Cannot Transfer to same Account");
		}
		
		AccountResponse sender = accountClient.getAccountByNumber(request.getFromAccountNumber());
		if(!sender.getUserId().equals(userId)) {
			throw new RuntimeException("You are not Authorized to use this Account");
		}
		
		
		AccountResponse receiver = accountClient.getAccountByNumber(request.getToAccountNumber());
		
//		if(sender.getBalance().compareTo(request.getAmount())<0) {
//			throw new RuntimeException("Insufficient Balance");
//		}
//		
//		BigDecimal senderBalance = sender.getBalance().subtract(request.getAmount());
//		
//		BigDecimal recBalance = receiver.getBalance().add(request.getAmount());
		
		UpdateBalanceRequest senderRequest = new UpdateBalanceRequest();
		senderRequest.setAccountNumber(sender.getAccountNumber());
		senderRequest.setAmount(request.getAmount());
//		accountClient.updateBalance(senderRequest);
		accountClient.debitAccount(senderRequest);
		
		UpdateBalanceRequest receiverRequest = new UpdateBalanceRequest();
		receiverRequest.setAccountNumber(receiver.getAccountNumber());
		receiverRequest.setAmount(request.getAmount());
//		accountClient.updateBalance(receiverRequest);
		accountClient.creditAccount(receiverRequest);
		
		
		//save transaction
		Transaction transaction = new Transaction();
		transaction.setFromAccountNumber(sender.getAccountNumber());
		transaction.setToAccountNumber(receiver.getAccountNumber());
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(TransactionType.TRANSFER);
		transaction.setTransactionStatus(TransactionStatus.SUCCESS);
		transaction.setCreatedAt(LocalDateTime.now());
		
		return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> getTransactionHistory(String accountNumber) {

		return transactionRepository.findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber);
	}
}
