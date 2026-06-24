package com.smartbank.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.smartbank.transaction.constants.TransactionStatus;
import com.smartbank.transaction.constants.TransactionType;

import jakarta.persistence.*;

@Entity
@Table(name="transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String fromAccountNumber;
	
	private String toAccountNumber;
	
	@Column(nullable=false)
	private BigDecimal amount;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TransactionType transactionType;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private TransactionStatus transactionStatus;
	
	@Column(nullable=false)
	private LocalDateTime createdAt;
	
	public Transaction() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
