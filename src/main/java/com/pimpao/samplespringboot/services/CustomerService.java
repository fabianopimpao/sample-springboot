package com.pimpao.samplespringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.dto.CustomerDto;
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

	public List<Customer> findAll() {
		List<Customer> customers = customerRepository.findAll(); 
		return customers;
	}
	
	public Customer update(Customer customer, Integer id) {
		Customer customerUpdate = this.findById(id);
		updateData(customerUpdate, customer);
		return customerRepository.save(customer);
	}	

	public void deleteById(Integer id) {
		this.findById(id);
		try {
			customerRepository.deleteById(id);			
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("Não é possível excluir clientes com endereços");
		}
	}
	
	public Page<Customer> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return customerRepository.findAll(pageRequest);
	}
	
	public Customer fromDto(CustomerDto customerDto) {
		return new Customer(customerDto.getId(), customerDto.getName(), customerDto.getEmail(), null, null);
	}
	
	private void updateData(Customer customerUpdate, Customer customer) {
		customerUpdate.setName(customer.getName());
		customerUpdate.setEmail(customer.getEmail());
	}
}