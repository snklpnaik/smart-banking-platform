package com.smartbank.account.dto;

import com.smartbank.account.constants.AccountType;

public class CreateAccountRequest {

	private Long userId;
	
	private AccountType accountType;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
}
