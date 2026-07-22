package com.smartbank.notification.service;

import com.smartbank.notification.dto.TransactionEvent;

public interface EmailService {
	void sendTransactional(TransactionEvent event);
	
	void sendDepositMail(TransactionEvent event);
	
	void sendWithdrawMail(TransactionEvent event);
	
	void sendTransferDebitMail(TransactionEvent event);
	
	void sendTransferCreditMail(TransactionEvent event);
}
