package com.pimpao.samplespringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pimpao.samplespringboot.domain.Address;
import com.pimpao.samplespringboot.domain.City;
import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.domain.enums.CustomerType;
import com.pimpao.samplespringboot.dto.CustomerDto;
import com.pimpao.samplespringboot.dto.CustomerNewDto;
import com.pimpao.samplespringboot.repositories.AddressRepository;
import com.pimpao.samplespringboot.repositories.CustomerRepository;
import com.pimpao.samplespringboot.services.exceptions.ObjectNotFoundException;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	public Customer findById(Integer id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.orElseThrow(() -> new ObjectNotFoundException("Object not found with id: " + id 
				+ ", type: " + Customer.class.getName()));
	}
	
	@Transactional
	public Customer save(Customer customer) {
		customer.setId(null);
		customer = customerRepository.save(customer);
		addressRepository.saveAll(customer.getAddresses());
		return customer;
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
	
	public Customer fromDto(CustomerNewDto customerNewDto) {
		Customer customer = new Customer(null, customerNewDto.getName(), customerNewDto.getEmail(), customerNewDto.getCpfOrCnpj(), CustomerType.toEnum(customerNewDto.getType()));
		City city = new City(customerNewDto.getCityId(), null, null);
		Address address = new Address(null, customerNewDto.getStreet(), customerNewDto.getNumber(), customerNewDto.getComplement(), customerNewDto.getNeighborhood(), customerNewDto.getZipCode(), customer, city);
		
		customer.getAddresses().add(address);
		customer.getTelephones().add(customerNewDto.getTelephone1());
		
		if (customerNewDto.getTelephone2() != null) {
			customer.getTelephones().add(customerNewDto.getTelephone2());
		}
		
		if (customerNewDto.getTelephone3() != null) {
			customer.getTelephones().add(customerNewDto.getTelephone3());
		}		
		
		return customer;
	}
	
	private void updateData(Customer customerUpdate, Customer customer) {
		customerUpdate.setName(customer.getName());
		customerUpdate.setEmail(customer.getEmail());
	}
	
}