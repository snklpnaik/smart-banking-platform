package com.smartbank.transaction.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public TransactionProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate=kafkaTemplate;
	}
	
	public void sendTransactionEvent(String message) {
		kafkaTemplate.send("transaction-events", message);
	}
}
