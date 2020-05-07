package br.com.spring_data_jpa.multiple_datasources.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.spring_data_jpa.multiple_datasources.datasources.postgresql.models.Client;
import br.com.spring_data_jpa.multiple_datasources.datasources.postgresql.repositories.ClientRepository;
import br.com.spring_data_jpa.multiple_datasources.services.exceptions.ClientNotFoundException;

@Service
public class ClientService {

	@Autowired
	@Qualifier("clientRepository")
	protected ClientRepository clientRepository;
	
	public Client findById(Integer id) throws ClientNotFoundException {
		Optional<Client> client = this.clientRepository.findById(id);
		if(!client.isPresent()) {
			throw new ClientNotFoundException();
		}
		return client.get();
	}
	
	public List<Client> findAll() throws ClientNotFoundException {
		List<Client> all = this.clientRepository.findAll();
		if(all == null || all.isEmpty()) {
			throw new ClientNotFoundException();
		}
		return all;
	}
	
	public Client save(Client client) {
		// TODO: implements validations
		return this.clientRepository.save(client);
	}
}
