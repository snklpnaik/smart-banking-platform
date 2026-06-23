package com.smartbank.auth.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartbank.auth.entity.User;
import com.smartbank.auth.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email)
									.orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
		
		return new CustomUserDetails(user);
	}
}
