package com.smartbank.apigateway.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.smartbank.apigateway.constants.SecurityConstants;
import com.smartbank.apigateway.util.JwtUtil;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered{
	
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public int getOrder() {
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		String path = exchange.getRequest().getURI().getPath();
		
		if(path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
			return chain.filter(exchange);
		}
		
		String authHeader = exchange.getRequest()
									.getHeaders()
									.getFirst(HttpHeaders.AUTHORIZATION);
		
		if(authHeader==null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			
			return exchange.getResponse().setComplete();
			
		}
		
		
		//Token is received as Authorization: Bearer eyoahagshh1hnbus8jghU.
		//Below code is to remove Bearer 
		String token = authHeader.substring(SecurityConstants.TOKEN_PREFIX.length());
		if(!jwtUtil.validateToken(token)) {
			
			exchange.getResponse()
					.setStatusCode(HttpStatus.UNAUTHORIZED);

			return exchange.getResponse().setComplete();
		}
		
		String email = jwtUtil.extractEmail(token);
		ServerHttpRequest request = exchange.getRequest()
											.mutate()
											.header("X-Authenticated-User", email)
											.build();
		ServerWebExchange modifiedExchange = exchange.mutate()
													.request(request)
													.build();
		
		
		return chain.filter(modifiedExchange);
	}
}
