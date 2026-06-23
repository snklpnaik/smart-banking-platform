package com.smartbank.account.util;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.smartbank.account.constants.AccountConstants;
import com.smartbank.account.constants.AccountType;
import com.smartbank.account.entity.Account;
import com.smartbank.account.repository.AccountRepository;

@Component
public class AccountNumberGenerator {
	private final AccountRepository accountRepository;
	
	public AccountNumberGenerator(AccountRepository accountRepository) {
		this.accountRepository=accountRepository;
	}
	
	
	public String generateAccountNumber(AccountType accountType) {
		String prefix = accountType.getPrefix();
		
		Optional<Account> latestAccount = accountRepository.findTopByAccountTypeOrderByIdDesc(accountType);
		
		long nextSequence=0;
		
		if(latestAccount.isEmpty()) {
			nextSequence = AccountConstants.ACCOUNT_START_SEQUENCE;
		} else {
			String lastAccountNumber = latestAccount.get().getAccountNumber();
			
			String numericPart = lastAccountNumber.substring(prefix.length());
			
			nextSequence = Long.parseLong(numericPart)+1;
		}
		
		
		
		return prefix + (nextSequence+1);
	}
}
