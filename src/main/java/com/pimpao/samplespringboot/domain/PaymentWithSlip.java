package com.pimpao.samplespringboot.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.pimpao.samplespringboot.domain.enums.PaymentStatus;

@Entity
public class PaymentWithSlip extends Payment {
	
	private static final long serialVersionUID = 1L;
	
	private Date dueDate;
	private Date paymentDate;
	
	public PaymentWithSlip() {
		
	}

	public PaymentWithSlip(Integer id, PaymentStatus status, Order order, Date dueDate, Date paymentDate) {
		super(id, status, order);
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}	
	
	
}
