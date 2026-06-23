package com.smartbank.account.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smartbank.account.constants.AccountStatus;
import com.smartbank.account.constants.AccountType;

import jakarta.persistence.*;

@Entity
@Table(name="accounts")
public class Account {
	
	public static Builder builder() {
		return new Builder();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String accountNumber;
	
	@Column(nullable = false)
	private Long userId;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private AccountType accountType;
	
	@Column(nullable=false)
	private BigDecimal balance;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private AccountStatus status;
	
	@Column(nullable=false)
	private LocalDate createdAt;
	
	public Account() {
		
	}
	
	private Account(Builder builder) {
		this.accountNumber=builder.accountNumber;
		this.userId=builder.userId;
		this.accountType=builder.accountType;
		this.balance=builder.balance;
		this.status=builder.status;
		this.createdAt=builder.createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
	public static class Builder{
		private String accountNumber;
		private Long userId;
		private AccountType accountType;
		private BigDecimal balance;
		private AccountStatus status;
		private LocalDate createdAt;
		
		public Builder userId(Long userId) {
			this.userId=userId;
			return this;
		}
		
		public Builder accountType(AccountType accountType) {
			this.accountType=accountType;
			return this;
		}
		
		public Builder accountNumber(String accountNumber) {
			this.accountNumber=accountNumber;
			return this;
		}
		
		public Builder balance(BigDecimal balance) {
			this.balance=balance;
			return this;
		}
		
		public Builder status(AccountStatus status) {
			this.status=status;
			return this;
		}
		
		public Builder createdAt(LocalDate createdAt) {
			this.createdAt=createdAt;
			return this;
		}
		
		public Account build() {
			return new Account(this);
		}
		
	}
	
}


