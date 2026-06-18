package com.smartbank.auth.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.smartbank.auth.constants.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwUtil {

	private final SecretKey key = Keys.hmacShaKeyFor("ThisSecretKeyIsBySankalpThisSecretKeyIsBySankalp".getBytes());
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
				.signWith(key)
				.compact();
	}
	
	public String extractEmail(String token) {
		
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
	}
	
	public boolean validateToken(String token, String email) {
		String extEmail = extractEmail(token);
		return extEmail.equals(email);
	}
}
