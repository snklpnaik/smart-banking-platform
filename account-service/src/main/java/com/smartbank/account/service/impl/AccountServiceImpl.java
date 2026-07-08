package com.smartbank.account.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smartbank.account.client.UserClient;
import com.smartbank.account.constants.AccountStatus;
import com.smartbank.account.dto.BalanceUpdateRequest;
import com.smartbank.account.dto.CreateAccountRequest;
import com.smartbank.account.dto.UpdateBalanceRequest;
import com.smartbank.account.dto.UserResponseDto;
import com.smartbank.account.entity.Account;
import com.smartbank.account.exception.AccountNotFoundException;
import com.smartbank.account.repository.AccountRepository;
import com.smartbank.account.service.AccountService;
import com.smartbank.account.util.AccountNumberGenerator;

@Service
public class AccountServiceImpl implements AccountService{

	private final AccountRepository accountRepository;
	
	private final AccountNumberGenerator accountNumberGenerator;
	
	private final UserClient userClient;
	
	public AccountServiceImpl(AccountRepository accountRepository, AccountNumberGenerator accountNumberGenerator, UserClient userClient) {
		this.accountRepository=accountRepository;
		this.accountNumberGenerator=accountNumberGenerator;
		this.userClient=userClient;
	}
	
	@Override
	public Account createAccount(CreateAccountRequest request) {

		
		ResponseEntity<UserResponseDto> response = userClient.getUserById(request.getUserId());
		if(!response.getStatusCode().is2xxSuccessful() || response.getBody()==null) {
			throw new RuntimeException("User Not Found!");
		}
		UserResponseDto authDto = response.getBody();
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
				.orElseThrow(() -> new AccountNotFoundException("Account Not Found: " + accountNumber));
	}

	@Override
	public List<Account> getAccountByUserId(Long userId) {

		return accountRepository.findByUserId(userId);
	}

	@Override
	public Account updateBalance(UpdateBalanceRequest request) {
		
		Account account = accountRepository.findByAccountNumber(
							request.getAccountNumber())
							.orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
		
		account.setBalance(request.getBalance());
		
		return accountRepository.save(account);
	}

	@Override
	public String test(Long id) {
		
		UserResponseDto user= userClient.getUserById(id).getBody();
		
		return "User Id : " + user.getId() + ", Username : " + user.getUsername() + ", Email : " + user.getEmail();
	}

	@Override
	public Account debitAccount(BalanceUpdateRequest request) {
		
		Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
								.orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
		
		if(account.getBalance().compareTo(request.getAmount())<0) {
			throw new RuntimeException("Insufficient Balance");
		}
		
		account.setBalance(account.getBalance().subtract(request.getAmount()));
		
		return accountRepository.save(account);
	}

	@Override
	public Account creditAccount(BalanceUpdateRequest request) {
		
		Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
								.orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
		
		account.setBalance(account.getBalance().add(request.getAmount()));
		
		return accountRepository.save(account);
	}
}
