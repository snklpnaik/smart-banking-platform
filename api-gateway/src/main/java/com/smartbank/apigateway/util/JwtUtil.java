package com.smartbank.apigateway.util;


import java.util.Date;

import javax.crypto.SecretKey;

import com.smartbank.apigateway.constants.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	private final SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET_KEY.getBytes());
	
	public boolean validateToken(String token) {
		try {
			
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	public Date getExpirationDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	public String getRole(String token) {
		return getClaims(token).get("role", String.class);
	}
}
