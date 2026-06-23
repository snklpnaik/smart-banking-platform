package com.smartbank.auth.service.impl;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartbank.auth.constants.Role;
import com.smartbank.auth.dto.LoginRequest;
import com.smartbank.auth.dto.LoginResponse;
import com.smartbank.auth.dto.ProfileResponse;
import com.smartbank.auth.dto.RegisterRequest;
import com.smartbank.auth.entity.User;
import com.smartbank.auth.exception.InvalidCredentialsException;
import com.smartbank.auth.exception.UserNotFoundException;
import com.smartbank.auth.repository.UserRepository;
import com.smartbank.auth.security.JwUtil;
import com.smartbank.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	
	private final JwUtil jwUtil;
	
	private final PasswordEncoder passwordEncoder;
	
	public AuthServiceImpl(UserRepository userRepository, JwUtil jwUtil, PasswordEncoder passwordEncoder) {
		this.userRepository=userRepository;
		this.jwUtil=jwUtil;
		this.passwordEncoder=passwordEncoder;
	}
	
	@Override
	public String register(RegisterRequest request) {

		if(userRepository.existsByEmail(request.getEmail())) {
			return "Email Already Exists";
		}
		
		User user = new User();
		
		user.setUserName(request.getUserName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		userRepository.save(user);
		
		return "User Registered Successfully";
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		
		
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserNotFoundException("User Not Found, Register First!"));
		
		if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			String token = jwUtil.generateToken(request.getEmail());
			return new LoginResponse(token);
		}
		
		throw new InvalidCredentialsException("Invalid Password");
	}

	@Override
	public ProfileResponse getProfile() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
									.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		
		return new ProfileResponse(
				user.getUserName(),
				user.getEmail());
	}
}
