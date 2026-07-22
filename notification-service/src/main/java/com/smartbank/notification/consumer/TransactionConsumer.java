package com.smartbank.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.smartbank.notification.dto.TransactionEvent;
import com.smartbank.notification.service.EmailService;

@Service
public class TransactionConsumer {
	
		private final EmailService emailService;
		
		public TransactionConsumer(EmailService emailService) {
			this.emailService=emailService;
		}
	
		@KafkaListener(topics = "transaction-events", groupId = "notification-group")
		public void consume(TransactionEvent event) {
			System.out.println("Message Received");
			
			System.out.println(event.getTransactionType());
			System.out.println(event.getAmount());
			
			switch(event.getTransactionType()) {
			
			case "DEPOSIT":
				emailService.sendDepositMail(event);
				break;
			
			case "WITHDRAW":
				emailService.sendWithdrawMail(event);
				break;
				
			case "TRANSFER":
				emailService.sendTransferDebitMail(event);
				emailService.sendTransferCreditMail(event);
				break;
				
			default:
				System.out.println("Unknown Transaction Type");	
				
			}
			
			
			
		}
}
