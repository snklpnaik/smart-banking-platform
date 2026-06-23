package com.smartbank.auth.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	private final CustomUserDetailsService customUserDetailsService;
	
	public JwtFilter(JwUtil jwUtil, CustomUserDetailsService customUserDetailsService) {
		this.jwUtil=jwUtil;
		this.customUserDetailsService=customUserDetailsService;
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
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
				
				System.out.println("Authenticated User : "+email);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails,
						null, 
						userDetails.getAuthorities());
				
				SecurityContextHolder.getContext()
									.setAuthentication(authentication);
				
				System.out.println("--------AUTHENTICATED---------");
			} catch(Exception e) {
				System.out.println("Invalid Token");
				
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType("application/json");
				response.getWriter().write(
						"{\"message\":\"Invalid Token\"}");
				return;
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	
}
