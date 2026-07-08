package com.smartbank.transaction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.transaction.dto.DepositRequest;
import com.smartbank.transaction.dto.TransferRequest;
import com.smartbank.transaction.dto.WithdrawRequest;
import com.smartbank.transaction.entity.Transaction;
import com.smartbank.transaction.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {
	private final TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService=transactionService;
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<Transaction> deposit(@RequestBody DepositRequest request) {
		return ResponseEntity.ok(transactionService.deposit(request));
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody WithdrawRequest request) {
		return ResponseEntity.ok(transactionService.withdraw(request));
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<Transaction> transfer(@RequestHeader("X-Authenticated-UserId") Long userId,
												@RequestBody TransferRequest request) {
		return ResponseEntity.ok(transactionService.transfer(request));
	}
	
	@GetMapping("/{accountNumber}")
	public List<Transaction> getTransactionHistory(@PathVariable String accountNumber){
		return transactionService.getTransactionHistory(accountNumber);
	}
}
