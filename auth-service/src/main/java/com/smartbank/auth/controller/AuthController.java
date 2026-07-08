package com.smartbank.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.auth.dto.LoginRequest;
import com.smartbank.auth.dto.LoginResponse;
import com.smartbank.auth.dto.ProfileResponse;
import com.smartbank.auth.dto.RegisterRequest;
import com.smartbank.auth.dto.UserResponseDto;
import com.smartbank.auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService=authService;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest request) {
		return authService.register(request);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		return authService.login(request);
	}
		
	@GetMapping("/profile")
	public ProfileResponse getProfile() {
		return authService.getProfile();
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "Admin Endpoint";
	}
	
	@GetMapping("/user/id/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
		return ResponseEntity.ok(authService.getUserById(id));
	}
}
