package com.smartbank.auth.constants;

public class SecurityConstants {
	private SecurityConstants() {
		
	}
	
	public static final String[] PUBLIC_URLS = {
			"/auth/register", 
			"/auth/login",
			"/auth/user/**"
	};
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	public static final long JWT_EXPIRATION = 100*60*60;
	
	public static final String JWT_SECRET_KEY = "ThisSecretKeyIsBySankalpThisSecretKeyIsBySankalp";
}
