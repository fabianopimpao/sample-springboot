package com.pimpao.samplespringboot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.pimpao.samplespringboot.domain.Category;

public class CategoryDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Preenchimmento obrigatório")
	@Length(min = 5, max= 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
	private String name;
	
	public CategoryDto() {
	
	}
	
	public CategoryDto(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
}
