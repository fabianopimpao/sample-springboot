package com.pimpao.samplespringboot.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pimpao.samplespringboot.domain.Product;
import com.pimpao.samplespringboot.dto.ProductDto;
import com.pimpao.samplespringboot.resources.utils.URL;
import com.pimpao.samplespringboot.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> findById(@PathVariable("id") Integer id) {
		Product product = productService.findById(id);
		return ResponseEntity.ok(product);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProductDto>> findPage(
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "categories", defaultValue = "") String categories,
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "size", defaultValue = "24") Integer size,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "diretion", defaultValue = "ASC") String direction) {
		
		String decodedName = URL.decodeParam(name);
		List<Integer> ids = URL.decodeIntList(categories);
		Page<Product> products = productService.search(decodedName, ids, page, size, orderBy, direction);
		Page<ProductDto> productsDto = products.map(product -> new ProductDto(product));
		
		return ResponseEntity.ok(productsDto);
	}
	
}
