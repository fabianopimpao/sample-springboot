package com.pimpao.samplespringboot.domain.enums;

public enum PaymentStatus {
	
	PEDING(1, "Peding"),
	PAID(2, "Paid"),
	CANCELED(3, "Canceled");

	private int code;
	private String description;
	
	private PaymentStatus(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return code;
	}


	public String getDescription() {
		return description;
	}
	
	public static PaymentStatus toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		
		for (PaymentStatus status : PaymentStatus.values()) {
			if (code.equals(status.code)) {
				return status;
			} 
		}
		
		throw new IllegalArgumentException("Invalid code: " + code);
	}

	
}
