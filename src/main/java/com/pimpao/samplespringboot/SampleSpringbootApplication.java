package com.pimpao.samplespringboot;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pimpao.samplespringboot.domain.Address;
import com.pimpao.samplespringboot.domain.Category;
import com.pimpao.samplespringboot.domain.City;
import com.pimpao.samplespringboot.domain.Customer;
import com.pimpao.samplespringboot.domain.Order;
import com.pimpao.samplespringboot.domain.OrderItem;
import com.pimpao.samplespringboot.domain.Payment;
import com.pimpao.samplespringboot.domain.PaymentWithCard;
import com.pimpao.samplespringboot.domain.PaymentWithSlip;
import com.pimpao.samplespringboot.domain.Product;
import com.pimpao.samplespringboot.domain.State;
import com.pimpao.samplespringboot.domain.enums.CustomerType;
import com.pimpao.samplespringboot.domain.enums.PaymentStatus;
import com.pimpao.samplespringboot.repositories.AddressRepository;
import com.pimpao.samplespringboot.repositories.CategoryRepository;
import com.pimpao.samplespringboot.repositories.CityRepository;
import com.pimpao.samplespringboot.repositories.CustomerRepository;
import com.pimpao.samplespringboot.repositories.OrderItemRepository;
import com.pimpao.samplespringboot.repositories.OrderRepository;
import com.pimpao.samplespringboot.repositories.PaymentRepository;
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
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
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
				
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Order ord1 = new Order(null, sdf.parse("30/09/2017 10:32"), cus1, a1);
		Order ord2 = new Order(null, sdf.parse("10/10/2017 19:35"), cus1, a2);
		
		Payment pmt1 = new PaymentWithCard(null, PaymentStatus.PAID, ord1, 6);
		ord1.setPayment(pmt1);
		Payment pmt2 = new PaymentWithSlip(null, PaymentStatus.PEDING, ord2, sdf.parse("20/10/2017 00:00"), null);
		ord2.setPayment(pmt2);
		
		cus1.getOrders().addAll(Arrays.asList(ord1, ord2));
		
		orderRepository.saveAll(Arrays.asList(ord1, ord2));
		paymentRepository.saveAll(Arrays.asList(pmt1, pmt2));
		
		OrderItem orderItem1 = new OrderItem(ord1, prod1, 0.00, 1, 2000.00);
		OrderItem orderItem2 = new OrderItem(ord1, prod3, 0.00, 2, 80.00);
		OrderItem orderItem3 = new OrderItem(ord2, prod2, 100.00, 1, 800.00);
		
		ord1.getItems().addAll(Arrays.asList(orderItem1, orderItem2));
		ord2.getItems().add(orderItem3);
		
		prod1.getItems().add(orderItem1);
		prod2.getItems().add(orderItem3);
		prod3.getItems().add(orderItem2);
		
		orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3));
		
	}

}
