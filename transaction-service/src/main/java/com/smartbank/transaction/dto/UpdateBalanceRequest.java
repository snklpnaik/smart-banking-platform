package com.smartbank.transaction.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateBalanceRequest {
	
	@NotBlank(message="Account Number is Required")
	private String accountNumber;
	
	@NotNull(message="Amount is required")
	@PositiveOrZero(message="Amount cannot be negative")
	private BigDecimal amount;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber=accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
}
