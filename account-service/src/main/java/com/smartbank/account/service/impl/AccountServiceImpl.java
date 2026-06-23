package com.smartbank.account.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartbank.account.constants.AccountStatus;
import com.smartbank.account.dto.CreateAccountRequest;
import com.smartbank.account.entity.Account;
import com.smartbank.account.repository.AccountRepository;
import com.smartbank.account.service.AccountService;
import com.smartbank.account.util.AccountNumberGenerator;

@Service
public class AccountServiceImpl implements AccountService{

	private final AccountRepository accountRepository;
	
	private final AccountNumberGenerator accountNumberGenerator;
	
	public AccountServiceImpl(AccountRepository accountRepository, AccountNumberGenerator accountNumberGenerator) {
		this.accountRepository=accountRepository;
		this.accountNumberGenerator=accountNumberGenerator;
	}
	
	@Override
	public Account createAccount(CreateAccountRequest request) {

		Account account = Account.builder()
									.userId(request.getUserId())
									.accountType(request.getAccountType())
									.accountNumber(accountNumberGenerator.generateAccountNumber(request.getAccountType()))
									.balance(BigDecimal.ZERO)
									.status(AccountStatus.ACTIVE)
									.createdAt(LocalDate.now())
									.build();
		
		return accountRepository.save(account);
	}

	@Override
	public Account getAccountByAccountNumber(String accountNumber) {
		
		return accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new RuntimeException("Account Not Found"));
	}

	@Override
	public List<Account> getAccountByUserId(Long userId) {

		return accountRepository.findByUserId(userId);
	}
}
