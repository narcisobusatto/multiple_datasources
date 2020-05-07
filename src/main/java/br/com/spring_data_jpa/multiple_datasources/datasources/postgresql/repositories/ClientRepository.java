package br.com.spring_data_jpa.multiple_datasources.datasources.postgresql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.spring_data_jpa.multiple_datasources.datasources.postgresql.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

}
