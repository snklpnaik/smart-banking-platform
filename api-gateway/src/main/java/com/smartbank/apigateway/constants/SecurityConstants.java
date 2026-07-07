package com.smartbank.apigateway.constants;

public class SecurityConstants {
	private SecurityConstants() {
		
	}
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	public static final long JWT_EXPIRATION = 100*60*60;
	
	public static final String JWT_SECRET_KEY = "ThisSecretKeyIsBySankalpThisSecretKeyIsBySankalp";
}
