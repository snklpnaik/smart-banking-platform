package com.smartbank.auth.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartbank.auth.entity.User;

public class CustomUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private final User user;
	
	public CustomUserDetails(User user) {
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(
				new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	
	
	

}
