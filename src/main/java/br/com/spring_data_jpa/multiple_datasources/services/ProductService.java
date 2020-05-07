package br.com.spring_data_jpa.multiple_datasources.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.spring_data_jpa.multiple_datasources.datasources.mysql.models.Product;
import br.com.spring_data_jpa.multiple_datasources.datasources.mysql.repositories.ProductRepository;
import br.com.spring_data_jpa.multiple_datasources.services.exceptions.ProductNotFoundException;

@Service
public class ProductService {

	@Autowired
	@Qualifier("productRepository")
	protected ProductRepository productRepository;

	public Product findById(Integer id) throws ProductNotFoundException {
		Optional<Product> product = this.productRepository.findById(id);
		if (!product.isPresent()) {
			throw new ProductNotFoundException();
		}
		return product.get();
	}

	public List<Product> findAll() throws ProductNotFoundException {
		List<Product> all = this.productRepository.findAll();
		if (all == null || all.isEmpty()) {
			throw new ProductNotFoundException();
		}
		return all;
	}

	public Product save(Product product) {
		return this.productRepository.save(product);
	}

}
