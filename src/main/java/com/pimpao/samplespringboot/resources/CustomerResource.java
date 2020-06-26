package com.pimpao.samplespringboot.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.dto.CategoryDto;
import com.pimpao.samplespringboot.dto.CustomerDto;
import com.pimpao.samplespringboot.services.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Customer> find(@PathVariable("id") Integer id) {
		Customer customer = customerService.findById(id);
		return ResponseEntity.ok().body(customer);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDto>> findAll() {
		List<Customer> customers = customerService.findAll();
		List<CustomerDto> customersDto = customers.stream().map(customer -> new CustomerDto(customer)).collect(Collectors.toList());
		return ResponseEntity.ok().body(customersDto);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@Valid @RequestBody CustomerDto customerDto, @PathVariable("id") Integer id) {
		Customer customer = customerService.fromDto(customerDto);
		customer = customerService.update(customer, id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		customerService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CustomerDto>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "24") Integer size, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
		
		Page<Customer> customers = customerService.findPage(page, size, orderBy, direction);
		Page<CustomerDto> customersDto = customers.map(customer -> new CustomerDto(customer));
		
		return ResponseEntity.ok().body(customersDto);		
	}
}
