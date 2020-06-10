package com.pimpao.samplespringboot.domain.enums;

public enum CustomerType {
	PRIVATE_INDIVIDUAL(1, "Private Individual"), 
	ENTITY_LEGAL(2, "Entity Legal");
	
	private int code;
	private String description;
	
	private CustomerType(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public static CustomerType toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		
		for (CustomerType customerType : CustomerType.values()) {
			if (code.equals(customerType.getCode())) {
				return customerType;
			}
		}
		
		throw new IllegalArgumentException("Invalid code: " + code);
	}
}
