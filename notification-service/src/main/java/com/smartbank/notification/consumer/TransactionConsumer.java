package com.smartbank.notification.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.smartbank.notification.dto.TransactionEvent;

@Service
public class TransactionConsumer {
		@KafkaListener(topics = "transaction-events", groupId = "notification-group")
		public void consume(TransactionEvent event) {
			System.out.println("Message Received");
			
			System.out.println(event.getTransactionType());
			System.out.println(event.getAmount());
		}
}
