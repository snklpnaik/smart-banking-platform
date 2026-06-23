package com.smartbank.auth.exception;

public class InvalidCredentialsException extends RuntimeException {
	public InvalidCredentialsException(String message) {
		super(message);
	}
}
