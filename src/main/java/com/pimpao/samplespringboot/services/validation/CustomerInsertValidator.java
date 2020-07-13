package com.pimpao.samplespringboot.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.domain.enums.CustomerType;
import com.pimpao.samplespringboot.dto.CustomerNewDto;
import com.pimpao.samplespringboot.repositories.CustomerRepository;
import com.pimpao.samplespringboot.resources.exception.FieldMessage;
import com.pimpao.samplespringboot.services.validation.utils.BR;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDto> {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public void initialize(CustomerInsert constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(CustomerNewDto customerNewDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		if (customerNewDto.getType().equals(CustomerType.PRIVATE_INDIVIDUAL.getCode()) && !BR.isValidCPF(customerNewDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CPF Inválido"));
		}
		
		if (customerNewDto.getType().equals(CustomerType.ENTITY_LEGAL.getCode()) && !BR.isValidCNPJ(customerNewDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CNPJ Inválido"));
		}
		
		Customer customer = customerRepository.findByEmail(customerNewDto.getEmail());
		
		if (customer != null) {
			list.add(new FieldMessage("email", "Já existe cliente com email informado"));
		}
		
		for (FieldMessage fieldMessage: list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
				.addPropertyNode(fieldMessage.getFieldName())
				.addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
