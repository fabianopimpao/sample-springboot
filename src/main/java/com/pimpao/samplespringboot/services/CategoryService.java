package com.pimpao.samplespringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.dto.CategoryDto;
import com.pimpao.samplespringboot.repositories.CategoryRepository;
import com.pimpao.samplespringboot.services.exceptions.DataIntegrityException;
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

	public Category update(Category category, Integer id) {
		Category categoryUpdate = this.findById(id);
		updateData(categoryUpdate, category);
		return categoryRepository.save(category);
	}

	public void deleteById(Integer id) {
		this.findById(id);
		try {			
			categoryRepository.deleteById(id);		
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityException("Não é possível excluir categorias com produtos");
		}
	}

	public List<Category> findAll() {
		List<Category> categories = categoryRepository.findAll();		
		return categories;
	}
	
	public Page<Category> findPage(Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		return categoryRepository.findAll(pageRequest);
	}
	
	public Category fromDto(CategoryDto categoryDto) {
		return new Category(categoryDto.getId(), categoryDto.getName());
	}
	
	private void updateData(Category categoryUpdate, Category category) {
		categoryUpdate.setName(category.getName());		
	}
}
