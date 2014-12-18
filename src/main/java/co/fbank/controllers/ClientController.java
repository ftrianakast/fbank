package co.fbank.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.fbank.model.Client;
import co.fbank.persistence.ClientRepository;

@Controller
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	public ClientController() {
	}

	@RequestMapping(value = "/clients", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public String addClient(@Valid @RequestBody Client client) {
		try {
			clientRepository.save(client);
			return "User succesfully added with id: " + client.getId();
		} catch (Exception e) {
			return "Error deleting the client:" + e.toString();
		}
	}
	
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Client getClient(@PathVariable Long id){
		Client searchedClient = clientRepository.findOne(id);
		return searchedClient;
	}
	
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ResponseBody
	public String deleteClient(@PathVariable Long id) {
		try {
			clientRepository.delete(id);
			return "User was successfully deleted";
		} catch (Exception e) {
			return "Error deleting the client. Its probably that the required client doesn't not exist";
		}
	}
}
