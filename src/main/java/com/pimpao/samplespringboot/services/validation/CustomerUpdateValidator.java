package com.pimpao.samplespringboot.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.dto.CustomerDto;
import com.pimpao.samplespringboot.repositories.CustomerRepository;
import com.pimpao.samplespringboot.resources.exception.FieldMessage;

public class CustomerUpdateValidator implements ConstraintValidator<CustomerUpdate, CustomerDto> {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void initialize(CustomerUpdate constraintAnnotation) {
	
	}
	
	@Override
	public boolean isValid(CustomerDto customerDto, ConstraintValidatorContext context) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Customer customer = customerRepository.findByEmail(customerDto.getEmail());
		
		if (customer != null && !customer.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Já existe cliente com email informado"));
		}
		
		for (FieldMessage field : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(field.getMessage())
				.addPropertyNode(field.getFieldName())
				.addConstraintViolation();
			
		}
		
		return list.isEmpty();
	}

}
