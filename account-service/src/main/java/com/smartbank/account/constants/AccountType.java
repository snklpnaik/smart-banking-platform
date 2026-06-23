package com.smartbank.account.constants;

public enum AccountType {
	SAVINGS("SB"),
	CURRENT("CA");
	
	private final String prefix;
	
	AccountType(String prefix){
		this.prefix=prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
}
