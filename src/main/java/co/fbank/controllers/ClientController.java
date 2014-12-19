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

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
@Controller
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	/**
	 * 
	 * @param client
	 * @return
	 */
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

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Client getClient(@PathVariable Long id) {
		Client searchedClient = clientRepository.findOne(id);
		return searchedClient;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
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

	/**
	 * 
	 * @param id
	 *            Its the client id
	 * @param client
	 *            Its the new representation of the client
	 * @return
	 */
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ResponseBody
	public String updateClient(@PathVariable Long id, @RequestBody Client client) {
		try {
			Client oldClient = clientRepository.findOne(id);
			String response = "";
			if (oldClient != null) {
				client.setId(oldClient.getId());
				clientRepository.save(client);
				response += "The client was updated with new information";
			} else {
				response += "The client you want update doesn't exist";
			}
			return response;
		} catch (Exception e) {
			return "There was an error updating the client";
		}
	}
}
