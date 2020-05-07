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

import br.com.spring_data_jpa.multiple_datasources.datasources.postgresql.models.Client;
import br.com.spring_data_jpa.multiple_datasources.services.ClientService;
import br.com.spring_data_jpa.multiple_datasources.services.exceptions.ClientNotFoundException;

@RestController
@RequestMapping("/clients")
public class ClientResource {

	@Autowired
	protected ClientService clientService;
	
	@GetMapping
	public ResponseEntity<List<Client>> findAll(){
		List<Client> clients = new ArrayList<Client>();
		try {
			clients = this.clientService.findAll();
		} catch (ClientNotFoundException e) {
			return ResponseEntity.notFound().build(); 
		}
		return ResponseEntity.ok(clients);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Client> findById(@PathVariable Integer id) {
		Client client = this.clientService.findById(id);
		try {
			client = this.clientService.findById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(client);
	}
	
	@PostMapping
	public ResponseEntity<Client> create(@Valid @RequestBody Client client, HttpServletResponse response) {
		Client clientCreated = this.clientService.save(client);
		return ResponseEntity.status(HttpStatus.CREATED).body(clientCreated);
	}

}
