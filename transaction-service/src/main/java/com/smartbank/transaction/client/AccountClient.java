package com.smartbank.transaction.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.smartbank.transaction.dto.UpdateBalanceRequest;
import com.smartbank.transaction.dto.client.AccountResponse;

@FeignClient(
		name="account-service",
		url="http://localhost:8082")
public interface AccountClient {
	
	@GetMapping("/accounts/{accountNumber}")
	AccountResponse getAccountByNumber(@PathVariable("accountNumber") String accountNumber);
	
	@PutMapping("accounts/balance")
	AccountResponse updateBalance(@RequestBody UpdateBalanceRequest request);
}
