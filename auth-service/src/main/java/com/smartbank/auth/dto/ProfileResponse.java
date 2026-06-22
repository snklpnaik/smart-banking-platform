package com.smartbank.auth.dto;

public class ProfileResponse {
	
	private String userName;
	private String email;
	
	public ProfileResponse() {
		
	}
	
	public ProfileResponse(String userName, String email) {
		this.userName=userName;
		this.email=email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
