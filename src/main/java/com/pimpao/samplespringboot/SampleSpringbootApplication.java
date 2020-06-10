package com.pimpao.samplespringboot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pimpao.samplespringboot.domain.Address;
import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.domain.City;
import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.domain.Product;
import com.pimpao.samplespringboot.domain.State;
import com.pimpao.samplespringboot.domain.enums.CustomerType;
import com.pimpao.samplespringboot.repositories.AddressRepository;
import com.pimpao.samplespringboot.repositories.CategoryRepository;
import com.pimpao.samplespringboot.repositories.CityRepository;
import com.pimpao.samplespringboot.repositories.CustomerRepository;
import com.pimpao.samplespringboot.repositories.ProductRepository;
import com.pimpao.samplespringboot.repositories.StateRepository;

@SpringBootApplication
public class SampleSpringbootApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository categoryRespository;
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SampleSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		
		Product prod1 = new Product(null, "Computador", 2000.00);
		Product prod2 = new Product(null, "Impressora", 800.00);
		Product prod3 = new Product(null, "Mouse", 80.00);
		
		cat1.getProducts().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProducts().add(prod2);
		
		prod1.getCategories().add(cat1);
		prod2.getCategories().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategories().add(cat1);		
		
		categoryRespository.saveAll(Arrays.asList(cat1, cat2));		
		productRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		State st1 = new State(null, "Minas Gerais");
		State st2 = new State(null, "São Paulo");
		
		City c1 = new City(null, "Uberlândia", st1);
		City c2 = new City(null, "São Paulo", st2);
		City c3 = new City(null, "Campinas", st2);
		
		st1.getCities().add(c1);
		st2.getCities().addAll(Arrays.asList(c2, c3));
		
		stateRepository.saveAll(Arrays.asList(st1, st2));
		cityRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Customer cus1 = new Customer(null, "Maria Silva", "maria@gmail.com", "36378912377", CustomerType.PRIVATE_INDIVIDUAL);
		cus1.getTelephones().addAll(Arrays.asList("27363323", "93838393"));
		
		Address a1 = new Address(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cus1, c1);
		Address a2 = new Address(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cus1, c2);
		
		cus1.getAddresses().addAll(Arrays.asList(a1, a2));
		
		customerRepository.save(cus1);
		addressRepository.saveAll(Arrays.asList(a1, a2));		
		
	}

}
