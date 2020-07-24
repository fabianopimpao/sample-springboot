package com.pimpao.samplespringboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.domain.Product;
import com.pimpao.samplespringboot.repositories.CategoryRepository;
import com.pimpao.samplespringboot.repositories.ProductRepository;
import com.pimpao.samplespringboot.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Product findById(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		return product.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! Id: " + id
				+ " Tipo: " + Product.class.getName()));
	}
	
	public Page<Product> search(String name, List<Integer> ids, Integer page, Integer size, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);
		List<Category> categories = categoryRepository.findAllById(ids);
		return productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
	}
	
}
