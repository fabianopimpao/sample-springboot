package com.pimpao.samplespringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.repositories.CustomerRepository;
import com.pimpao.samplespringboot.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	public Customer findById(Integer id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id 
				+ ", type: " + Customer.class.getName()));
	}

}
