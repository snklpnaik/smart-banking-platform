package com.smartbank.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smartbank.transaction.dto.UpdateBalanceRequest;
import com.smartbank.transaction.dto.client.AccountResponse;

@FeignClient(
		name="ACCOUNT-SERVICE")
public interface AccountClient {
	
	@GetMapping("/accounts/{accountNumber}")
	AccountResponse getAccountByNumber(@PathVariable("accountNumber") String accountNumber);
	
	@PutMapping("accounts/balance")
	AccountResponse updateBalance(@RequestBody UpdateBalanceRequest request);
	
	@PutMapping("/accounts/debit")
	public ResponseEntity<AccountResponse> debitAccount(@RequestBody UpdateBalanceRequest request);
	
	@PutMapping("/accounts/credit")
	public ResponseEntity<AccountResponse> creditAccount(@RequestBody UpdateBalanceRequest request);
}
