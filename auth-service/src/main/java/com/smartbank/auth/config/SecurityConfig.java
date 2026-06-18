package com.smartbank.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.smartbank.auth.constants.SecurityConstants;
import com.smartbank.auth.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final JwtFilter jwFilter;
	
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwFilter=jwtFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		System.out.println("Inside security config-----------------");
		
//		http
//			.csrf(csrf -> csrf.disable())
//			.authorizeHttpRequests(auth -> auth
//											.anyRequest().permitAll())
//			.httpBasic(Customizer.withDefaults()
//					);
//		
//		return http.build();
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
						.requestMatchers(SecurityConstants.PUBLIC_URLS).permitAll()
						.anyRequest().authenticated())
			.addFilterBefore(jwFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
}
