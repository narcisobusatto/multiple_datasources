package br.com.spring_data_jpa.multiple_datasources.resources;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.spring_data_jpa.multiple_datasources.datasources.mysql.models.Product;
import br.com.spring_data_jpa.multiple_datasources.services.ProductService;
import br.com.spring_data_jpa.multiple_datasources.services.exceptions.ProductNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductResource {

	@Autowired
	protected ProductService productService;
	
	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> products = new ArrayList<Product>();
		try {
			products = this.productService.findAll();
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build(); 
		}
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable Integer id) {
		Product product = null;
		try {
			product = this.productService.findById(id);
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(product);
	}
	
	@PostMapping
	public ResponseEntity<Product> create(@Valid @RequestBody Product product, HttpServletResponse response) {
		Product productCreated = this.productService.save(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
	}

	
	
}
