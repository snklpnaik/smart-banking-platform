package com.smartbank.account.dto;

import com.smartbank.account.constants.AccountType;

import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {

	private Long userId;
	
	private AccountType accountType;

	@NotNull(message="UserId is Required")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@NotNull(message="Account Type is Required")
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
}
