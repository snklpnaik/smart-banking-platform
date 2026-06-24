package com.smartbank.transaction.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public class TransferRequest {
	
	@NotBlank(message="From Account Number is Required")
	private String fromAccountNumber;
	
	@NotBlank(message="To Account Number is Required")
	private String toAccountNumber;
	
	@NotNull(message="Amount is Required")
	@Positive(message="Amount must be greater than zero")
	private BigDecimal amount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}
}
