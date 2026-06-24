package com.smartbank.account.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.smartbank.account.dto.CreateAccountRequest;
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
	
}
