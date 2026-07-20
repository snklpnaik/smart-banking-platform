package com.smartbank.account.service;

import java.util.List;

import com.smartbank.account.dto.BalanceUpdateRequest;
import com.smartbank.account.dto.CreateAccountRequest;
import com.smartbank.account.dto.UpdateBalanceRequest;
import com.smartbank.account.entity.Account;

public interface AccountService {
	
	Account createAccount(CreateAccountRequest request);
	
	Account getAccountByAccountNumber(String accountNumber);
	
	List<Account> getAccountByUserId(Long userId);
	
	Account updateBalance(UpdateBalanceRequest request);
	
	String test(Long id);

	Account debitAccount(UpdateBalanceRequest request);
	
	Account creditAccount(UpdateBalanceRequest request);
}
