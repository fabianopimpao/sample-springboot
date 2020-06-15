package com.pimpao.samplespringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.repositories.CategoryRepository;
import com.pimpao.samplespringboot.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category findById(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);
		return category.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id: " + id
				+ ", tipo: " + Category.class.getName()));
	}

	public Category save(Category category) {
		category.setId(null);
		return categoryRepository.save(category);
	}
}
