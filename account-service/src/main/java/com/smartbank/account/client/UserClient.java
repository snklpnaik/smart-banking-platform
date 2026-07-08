package com.smartbank.account.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smartbank.account.dto.UserResponseDto;


@FeignClient(name = "AUTH-SERVICE")
public interface UserClient {
	@GetMapping("/auth/user/id/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id);
}
