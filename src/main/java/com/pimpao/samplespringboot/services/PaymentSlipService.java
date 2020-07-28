package com.pimpao.samplespringboot.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.PaymentWithSlip;

@Service
public class PaymentSlipService {

	public void calcutePaymentDate(PaymentWithSlip paymentWithSlip, Date instante) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(instante);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		paymentWithSlip.setPaymentDate(calendar.getTime());		
	}

}
