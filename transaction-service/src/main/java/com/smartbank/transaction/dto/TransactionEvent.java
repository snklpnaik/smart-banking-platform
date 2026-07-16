package com.smartbank.transaction.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {
	private String transactionType;
	private String fromAccount;
	private String toAccount;
	private BigDecimal amount;
	private String senderEmail;
	private String receiverEmail;

}
