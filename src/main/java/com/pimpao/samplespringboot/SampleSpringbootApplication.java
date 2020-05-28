package com.pimpao.samplespringboot;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.repositories.CategoryRepository;

@SpringBootApplication
public class SampleSpringbootApplication implements CommandLineRunner {
	
	@Autowired
	private CategoryRepository categoryRespository;
	
	public static void main(String[] args) {
		SpringApplication.run(SampleSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Category cat1 = new Category(null, "Informática");
		Category cat2 = new Category(null, "Escritório");
		
		categoryRespository.saveAll(Arrays.asList(cat1, cat2));
	}

}
