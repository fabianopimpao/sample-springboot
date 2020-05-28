package com.pimpao.samplespringboot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Category findById(Integer id) {
		Optional<Category> category = categoryRepository.findById(id);
		return category.orElse(null);
	}
}
