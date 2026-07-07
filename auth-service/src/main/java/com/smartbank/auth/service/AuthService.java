package com.smartbank.auth.service;

import com.smartbank.auth.dto.LoginRequest;
import com.smartbank.auth.dto.LoginResponse;
import com.smartbank.auth.dto.ProfileResponse;
import com.smartbank.auth.dto.RegisterRequest;
import com.smartbank.auth.dto.UserResponseDto;

public interface AuthService {
	String register(RegisterRequest request);

	LoginResponse login(LoginRequest request);
	
	ProfileResponse getProfile();
	
	UserResponseDto getUserByEmail(String email);
}
