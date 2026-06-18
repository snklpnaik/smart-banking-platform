package com.smartbank.auth.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartbank.auth.dto.LoginRequest;
import com.smartbank.auth.dto.RegisterRequest;
import com.smartbank.auth.entity.User;
import com.smartbank.auth.repository.UserRepository;
import com.smartbank.auth.security.JwUtil;
import com.smartbank.auth.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	
	private final JwUtil jwUtil;
	
	public AuthServiceImpl(UserRepository userRepository, JwUtil jwUtil) {
		this.userRepository=userRepository;
		this.jwUtil=jwUtil;
	}
	
	@Override
	public String register(RegisterRequest request) {

		if(userRepository.existsByEmail(request.getEmail())) {
			return "Email Already Exists";
		}
		
		User user = new User();
		
		user.setUserName(request.getUserName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		userRepository.save(user);
		
		return "User Registered Successfully";
	}

	@Override
	public String login(LoginRequest request) {
		
		if(!userRepository.existsByEmail(request.getEmail())) {
			return "User Not Found. Register First!";
		}
		
		Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
		
		User user=userOpt.get();
		
		if(user.getPassword().equals(request.getPassword())) {
			return jwUtil.generateToken(request.getEmail());
		}
		
		return "Invalid Password";
	}
}
