package com.smartbank.transaction.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartbank.transaction.client.AccountClient;
import com.smartbank.transaction.constants.TransactionStatus;
import com.smartbank.transaction.constants.TransactionType;
import com.smartbank.transaction.dto.DepositRequest;
import com.smartbank.transaction.dto.TransactionEvent;
import com.smartbank.transaction.dto.TransferRequest;
import com.smartbank.transaction.dto.UpdateBalanceRequest;
import com.smartbank.transaction.dto.WithdrawRequest;
import com.smartbank.transaction.dto.client.AccountResponse;
import com.smartbank.transaction.entity.Transaction;
import com.smartbank.transaction.kafka.TransactionProducer;
import com.smartbank.transaction.repository.TransactionRepository;
import com.smartbank.transaction.service.TransactionService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class TransactionServiceImpl implements TransactionService{
	private final TransactionRepository transactionRepository;
	private final AccountClient accountClient;
	private final TransactionProducer transactionProducer;
	
	public TransactionServiceImpl(TransactionRepository transactionRepository, AccountClient accountClient, TransactionProducer transactionProducer) {
		this.transactionRepository=transactionRepository;
		this.accountClient=accountClient;
		this.transactionProducer=transactionProducer;
	}

	@Override
	@CircuitBreaker(name="accountService", fallbackMethod="depositFallback")
	public Transaction deposit(DepositRequest request) {
		
		AccountResponse account = accountClient.getAccountByNumber(request.getAccountNumber());
		if(account==null) {

			return null;
		}
		BigDecimal newBalance = account.getBalance().add(request.getAmount());
		
		UpdateBalanceRequest updateRequest = new UpdateBalanceRequest();
		updateRequest.setAccountNumber(account.getAccountNumber());
		updateRequest.setBalance(newBalance);
		
		accountClient.updateBalance(updateRequest);
		
		Transaction transaction = new Transaction();
		transaction.setFromAccountNumber(null);
		transaction.setToAccountNumber(account.getAccountNumber());
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setTransactionStatus(TransactionStatus.SUCCESS);
		transaction.setCreatedAt(LocalDateTime.now());
		
		Transaction savedTransaction = transactionRepository.save(transaction);
		
		TransactionEvent event = new TransactionEvent();
		event.setTransactionType(TransactionType.DEPOSIT.name());
		event.setFromAccount(null);
		event.setToAccount(savedTransaction.getToAccountNumber());
		event.setAmount(savedTransaction.getAmount());
		event.setSenderEmail(null);
		event.setReceiverEmail(account.getEmail());
		
		transactionProducer.sendTransactionEvent(event);
		
		
//		transactionProducer.sendTransactionEvent(
//				"Deposit Successful: " + 
//						savedTransaction.getToAccountNumber() + 
//						" Amount: " + savedTransaction.getAmount()
//				);
		return savedTransaction;
	}

	@Override
	@CircuitBreaker(name="accountService", fallbackMethod="withdrawFallback")
	public Transaction withdraw(WithdrawRequest request) {
		
		AccountResponse account = accountClient.getAccountByNumber(request.getAccountNumber());
		if(account==null) {

			return null;
		}
		System.out.println(account+"::requestamount");
		
		if(account.getBalance()!=null && request.getAmount()!=null && account.getBalance().compareTo(request.getAmount()) < 0) { 
			
			throw new RuntimeException("Insufficient Balance");
			
		}
		BigDecimal newBalance = account.getBalance().subtract(request.getAmount());
		
		UpdateBalanceRequest updateRequest = new UpdateBalanceRequest();
		
		updateRequest.setAccountNumber(account.getAccountNumber());
		updateRequest.setBalance(newBalance);
		
		accountClient.updateBalance(updateRequest);
		
		Transaction transaction = new Transaction();
		
		transaction.setFromAccountNumber(account.getAccountNumber());
		transaction.setToAccountNumber(null);
		transaction.setAmount(request.getAmount());
		transaction.setTransactionType(TransactionType.WITHDRAW);
		transaction.setTransactionStatus(TransactionStatus.SUCCESS);
		transaction.setCreatedAt(LocalDateTime.now());
		
		Transaction savedTransaction = transactionRepository.save(transaction);
//		
//		transactionProducer.sendTransactionEvent(
//				"Withdraw Successful: " + 
//						savedTransaction.getFromAccountNumber() + 
//						" Amount: " + savedTransaction.getAmount()
//				);
		
		TransactionEvent event = new TransactionEvent();
		event.setTransactionType(TransactionType.WITHDRAW.name());
		event.setFromAccount(savedTransaction.getFromAccountNumber());
		event.setAmount(savedTransaction.getAmount());
		event.setToAccount(savedTransaction.getToAccountNumber());
		event.setSenderEmail(account.getEmail());
		event.setReceiverEmail(null);
		
		transactionProducer.sendTransactionEvent(event);
		
		
		return savedTransaction;
	}

	@Override
	@CircuitBreaker(name="accountService", fallbackMethod="transferFallback")
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
		senderRequest.setBalance(request.getAmount());
//		accountClient.updateBalance(senderRequest);
		accountClient.debitAccount(senderRequest);
		
		UpdateBalanceRequest receiverRequest = new UpdateBalanceRequest();
		receiverRequest.setAccountNumber(receiver.getAccountNumber());
		receiverRequest.setBalance(request.getAmount());
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
		
		Transaction savedTransaction = transactionRepository.save(transaction);
		
//		transactionProducer.sendTransactionEvent(
//				"Transfer Successful: " + 
//						savedTransaction.getFromAccountNumber() + " -> " + savedTransaction.getToAccountNumber() + 
//						" Amount: " + savedTransaction.getAmount()
//				);754247
		
		TransactionEvent event = new TransactionEvent();
		event.setTransactionType(TransactionType.TRANSFER.name());
		event.setFromAccount(savedTransaction.getFromAccountNumber());
		event.setToAccount(savedTransaction.getToAccountNumber());
		event.setAmount(savedTransaction.getAmount());
		event.setSenderEmail(sender.getEmail());
		event.setReceiverEmail(receiver.getEmail());
		
		transactionProducer.sendTransactionEvent(event);
		
		return savedTransaction;
	}

	@Override
	public List<Transaction> getTransactionHistory(String accountNumber) {

		return transactionRepository.findByFromAccountNumberOrToAccountNumber(accountNumber, accountNumber);
	}
	
	public Transaction depositFallback(DepositRequest request, Exception ex) {
		System.out.println("Circuit Breaker Activated: " + ex.getMessage());
		throw new RuntimeException("Account Service is temporarily unavailable. Please try again later");
	}
	
	public Transaction withdrawFallback(WithdrawRequest request, Exception ex) {
		System.out.println("Circuit Breaker Activated: " + ex.getMessage());
		throw new RuntimeException("Account Service is temporarily unavailable. Please try again later");
	}
	
	public Transaction transferFallback(TransferRequest request, Exception ex) {
		System.out.println("Circuit Breaker Activated: " + ex.getMessage());
		throw new RuntimeException("Account Service is temporarily unavailable. Please try again later");
	}
}
