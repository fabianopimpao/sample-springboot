package com.pimpao.samplespringboot.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Order;
import com.pimpao.samplespringboot.domain.OrderItem;
import com.pimpao.samplespringboot.domain.PaymentWithSlip;
import com.pimpao.samplespringboot.domain.enums.PaymentStatus;
import com.pimpao.samplespringboot.repositories.OrderItemRepository;
import com.pimpao.samplespringboot.repositories.OrderRepository;
import com.pimpao.samplespringboot.repositories.PaymentRepository;
import com.pimpao.samplespringboot.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentSlipService paymentSlipService;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	public Order findById(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		return order.orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id
				+ " type: " + Order.class.getName()));
	}

	public Order save(Order order) {
		order.setId(null);
		order.setInstante(new Date());
		order.getPayment().setStatus(PaymentStatus.PEDING);
		order.getPayment().setOrder(order);
		if (order.getPayment() instanceof PaymentWithSlip) {
			PaymentWithSlip paymentWithSlip = (PaymentWithSlip) order.getPayment();
			paymentSlipService.calcutePaymentDate(paymentWithSlip, order.getInstante());
		}
		
		Order newOrder = orderRepository.save(order);
		paymentRepository.save(newOrder.getPayment());
		
		for (OrderItem orderItem : newOrder.getItems()) {
			orderItem.setDiscount(0.0);
			orderItem.setPrice(productService.findById(orderItem.getProduct().getId()).getPrice());
			orderItem.setOrder(newOrder);
		}
		
		orderItemRepository.saveAll(order.getItems());
		
		return newOrder	;
	}
}
