package com.pimpao.samplespringboot.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.pimpao.samplespringboot.domain.enums.PaymentStatus;

@Entity
@JsonTypeName(value = "paymentWithCard")
public class PaymentWithCard extends Payment {
	
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfInstallments;
	
	public PaymentWithCard() {
		
	}

	public PaymentWithCard(Integer id, PaymentStatus status, Order order, Integer numberOfInstallments) {
		super(id, status, order);
		this.numberOfInstallments = numberOfInstallments;
	}

	public Integer getNumberOfInstallments() {
		return numberOfInstallments;
	}

	public void setNumberOfInstallments(Integer numberOfInstallments) {
		this.numberOfInstallments = numberOfInstallments;
	}	
	
}
