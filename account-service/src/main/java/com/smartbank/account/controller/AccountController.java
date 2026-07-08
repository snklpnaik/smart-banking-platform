package com.smartbank.account.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartbank.account.dto.BalanceUpdateRequest;
import com.smartbank.account.dto.CreateAccountRequest;
import com.smartbank.account.dto.UpdateBalanceRequest;
import com.smartbank.account.entity.Account;
import com.smartbank.account.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService=accountService;
	}
	
	
	@PostMapping
	public Account createAccount(@Valid @RequestBody CreateAccountRequest request) {
		return accountService.createAccount(request);
	}
	
	@GetMapping("/{accountNumber}")
	public Account getAccount(@PathVariable String accountNumber) {
		return accountService.getAccountByAccountNumber(accountNumber);
	}
	
	@GetMapping("/user/{userId}")
	public List<Account> getAccountByUser(@PathVariable Long userId){
		return accountService.getAccountByUserId(userId);
	}
	
	@PutMapping("/balance")
	public Account updateBalance(@Valid @RequestBody UpdateBalanceRequest request) {
		return accountService.updateBalance(request);
	}
	
	@PutMapping("/debit")
	public ResponseEntity<Account> debitAccount(@RequestBody BalanceUpdateRequest request){
		
		return ResponseEntity.ok(accountService.debitAccount(request));
	}
	
	@PutMapping("/credit")
	public ResponseEntity<Account> creditAccount(@RequestBody BalanceUpdateRequest request){
		
		return ResponseEntity.ok(accountService.creditAccount(request));
	}
	
	@GetMapping("/test")
	public String test(@RequestHeader("X-Authenticated-UserId") Long id,
			@RequestHeader("X-Authenticated-Email") String email, @RequestHeader("X-Authenticated-Role") String role) {
		System.out.println(id+email+role);
		return accountService.test(id);
	}
	
}
