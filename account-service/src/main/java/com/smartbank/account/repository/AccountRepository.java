package com.smartbank.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartbank.account.constants.AccountType;
import com.smartbank.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByAccountNumber(String accountNumber);
	
	List<Account> findByUserId(Long userId);
	
	Optional<Account> findTopByAccountTypeOrderByIdDesc(AccountType accountType);
}
