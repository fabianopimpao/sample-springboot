package com.pimpao.samplespringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Order;
import com.pimpao.samplespringboot.repositories.OrderRepository;
import com.pimpao.samplespringboot.services.exceptions.ObjectNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Order findById(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		return order.orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id
				+ " type: " + Order.class.getName()));
	}
}
