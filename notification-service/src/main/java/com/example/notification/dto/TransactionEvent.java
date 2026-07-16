package com.example.notification.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
	private LocalDateTime timestamp;

}
