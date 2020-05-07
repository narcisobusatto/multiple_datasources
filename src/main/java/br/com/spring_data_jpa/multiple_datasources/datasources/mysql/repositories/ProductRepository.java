package br.com.spring_data_jpa.multiple_datasources.datasources.mysql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring_data_jpa.multiple_datasources.datasources.mysql.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
