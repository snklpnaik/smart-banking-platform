package com.smartbank.auth.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartbank.auth.constants.SecurityConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	private final JwUtil jwUtil;
	
	public JwtFilter(JwUtil jwUtil) {
		this.jwUtil=jwUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		System.out.println("JWT Filter Executed");
		System.out.println("URI : "+ request.getRequestURI());
		
		String authHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
		
		if(authHeader!=null && authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			System.out.println("Authorization Header : "+ authHeader);
			
			String token = authHeader.substring(7);
			
			try {
				String email = jwUtil.extractEmail(token);
				
				System.out.println("Authenticated User : "+email);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						email,
						null, 
						Collections.emptyList());
				
				SecurityContextHolder.getContext()
									.setAuthentication(authentication);
				
				System.out.println("--------AUTHENTICATED---------");
			} catch(Exception e) {
				System.out.println("Invalid Token");
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	
}
