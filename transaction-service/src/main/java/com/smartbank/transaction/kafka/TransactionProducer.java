package com.smartbank.transaction.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.smartbank.transaction.dto.TransactionEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionProducer {
	private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
	
	public TransactionProducer(KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
		this.kafkaTemplate=kafkaTemplate;
	}
	
	public void sendTransactionEvent(TransactionEvent event) {
		kafkaTemplate.send("transaction-events", event);
	}
}
