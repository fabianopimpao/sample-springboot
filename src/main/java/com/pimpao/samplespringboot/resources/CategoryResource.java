package com.pimpao.samplespringboot.resources;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.dto.CategoryDto;
import com.pimpao.samplespringboot.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> find(@PathVariable("id") Integer id) {
		Category category = categoryService.findById(id);
		return ResponseEntity.ok().body(category);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody CategoryDto categoryDto) {
		Category category = categoryService.fromDto(categoryDto);
		category = categoryService.save(category);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(category.getId())
					.toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") Integer id) {
		Category category = categoryService.fromDto(categoryDto);
		category = categoryService.update(category, id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		categoryService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDto>> findAll() {
		List<Category> categories = categoryService.findAll();
		List<CategoryDto> categoriesDto = categories.stream().map(category -> new CategoryDto(category)).collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriesDto);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoryDto>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "24") Integer size, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		Page<Category> categries = categoryService.findPage(page, size, orderBy, direction);
		Page<CategoryDto> categoriesDto = categries.map(category -> new CategoryDto(category));
		
		return ResponseEntity.ok().body(categoriesDto);
	}
}
