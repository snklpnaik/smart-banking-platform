package com.smartbank.notification.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.smartbank.notification.dto.TransactionEvent;
import com.smartbank.notification.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	private final JavaMailSender mailSender;
	
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	@Override
	public void sendTransactional(TransactionEvent event) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		//message.setTo(event.getReceiverEmail());
		//Instead we will only send to snklpnaikwhatsapp@gmail.com for testing purpose
		message.setTo("snklpnaikwhatsapp@gmail.com");
		
		message.setSubject("Transaction Successful");
		message.setText("Transaction Type: " + event.getTransactionType() + event.getAmount());
		mailSender.send(message);
		
	}
	@Override
	public void sendDepositMail(TransactionEvent event) {
SimpleMailMessage message = new SimpleMailMessage();
		
		//message.setTo(event.getReceiverEmail());
		//Instead we will only send to snklpnaikwhatsapp@gmail.com for testing purpose
		message.setTo("snklpnaikwhatsapp@gmail.com");
		
		message.setSubject("Transaction Successful");
		
		message.setText(
				"Dear Customer, \n\n"
				+ "Your Account has been credited succesfully. \n\n"
				+ "Amount : Rs."
				+ event.getAmount()
				+ "\nAccount : "
				+ event.getToAccount()
				+ "\n\nThank you for banking with SmartBank."
				);
		mailSender.send(message);
		
	}
	@Override
	public void sendWithdrawMail(TransactionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("withdraw mail");
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		//message.setTo(event.getReceiverEmail());
		//Instead we will only send to snklpnaikwhatsapp@gmail.com for testing purpose
		message.setTo("snklpnaikwhatsapp@gmail.com");
		
		message.setSubject("Transaction Successful");
		
		message.setText(
				"Dear Customer, \n\n"
				+ "Your Account has been debited succesfully. \n\n"
				+ "Amount : Rs."
				+ event.getAmount()
				+ "\nAccount : "
				+ event.getFromAccount()
				+ "\n\nThank you for banking with SmartBank."
				);
		mailSender.send(message);
		
	}
	@Override
	public void sendTransferDebitMail(TransactionEvent event) {
		// TODO Auto-generated method stub
		System.out.println("transfer debit mail");
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		//message.setTo(event.getSenderEmail());
		//Instead we will only send to snklpnaikwhatsapp@gmail.com for testing purpose
		message.setTo("snklpnaikwhatsapp@gmail.com");
		
		message.setSubject("Amount Debited - SmartBank");
		
		message.setText(
				"Dear Customer, \n\n"
				+ "Rs." + event.getAmount() + " has been transferred succesfully\n"
				+ " from Account: " + event.getFromAccount()
				+ " to Account: " + event.getToAccount()
				+ "\n\nThank you for banking with SmartBank."
				);
		mailSender.send(message);

		
	}
	@Override
	public void sendTransferCreditMail(TransactionEvent event) {
		System.out.println("transfer credit mail");
		
		SimpleMailMessage message = new SimpleMailMessage();
				
		//message.setTo(event.getReceiverEmail());
		//Instead we will only send to snklpnaikwhatsapp@gmail.com for testing purpose
		message.setTo("snklpnaikwhatsapp@gmail.com");
		
		message.setSubject("Amount Credited - SmartBank");
		
		message.setText(
				"Dear Customer, \n\n"
				+ "Rs." + event.getAmount() + " has been transferred succesfully\n"
				+ " from Account: " + event.getFromAccount()
				+ " to Account: " + event.getToAccount()
				+ "\n\nThank you for banking with SmartBank."
				);
		mailSender.send(message);		

		
	}
}
