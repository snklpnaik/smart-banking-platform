package com.smartbank.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.smartbank.auth.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex){
		
		return new ResponseEntity<>(
				new ErrorResponse(
						ex.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex){
		return new ResponseEntity<>(
				new ErrorResponse(ex.getMessage()), HttpStatus.UNAUTHORIZED);		
	}
}
