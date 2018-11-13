package com.example.resttemplate;

import com.example.resttemplate.model.Product;
import com.example.resttemplate.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestTemplateApplication implements CommandLineRunner {

	private Logger LOG = LoggerFactory.getLogger(RestTemplateApplication.class);

	private ProductRepository productRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	public void productRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestTemplateApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		Product product1 = new Product();
		product1.setName("Tester Product");
		product1.setDescription("This is a tester product");
		product1.setCategory("TEST");
		product1.setType("GENERAL");
		product1.setPrice(0.0);

		// Save product1 to H2
		productRepository.save(product1);

		Product product2 = new Product();
		product2.setName("Another Tester Product");
		product2.setDescription("This is a tester product");
		product2.setCategory("TEST");
		product2.setType("CUSTOM");
		product2.setPrice(15.0);

		// Save product2 to H2
		productRepository.save(product2);

		Product product3 = new Product();
		product3.setName("Tester Product");
		product3.setDescription("description");
		product3.setCategory("TEST");
		product3.setType("SPECIFIC");
		product3.setPrice(19.0);

		// Save product3 to H2
		productRepository.save(product3);

		Product productFromRestTemplate = restTemplate.getForObject("http://localhost:8080/api/products/" + "ANY_ID", Product.class);

		LOG.info("Product received with restTemplate:" + productFromRestTemplate.toString());
	}
}
