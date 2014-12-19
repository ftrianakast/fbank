package co.fbank.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.fbank.controllers.aux.ReportRequest;
import co.fbank.domain.Client;
import co.fbank.persistence.ClientRepository;
import co.fbank.services.report.Report;
import co.fbank.services.report.ReportGenerator;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
@Controller
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ReportGenerator reportGenerator;
	
	/**
	 * 
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "/clients", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> addClient(@Valid @RequestBody Client client) {
		try {
			clientRepository.save(client);
			return new ResponseEntity<String>(
					"User succesfully added with id: " + client.getId(),
					HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Error deleting the client:"
					+ e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
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
	@ResponseBody
	public ResponseEntity<String> deleteClient(@PathVariable Long id) {
		try {
			clientRepository.delete(id);
			return new ResponseEntity<String>("User was successfully deleted",
					HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<String>(
					"Error deleting the client. Its probably that the required client doesn't not exist",
					HttpStatus.INTERNAL_SERVER_ERROR);
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
	@ResponseBody
	public ResponseEntity<String> updateClient(@PathVariable Long id,
			@RequestBody Client client) {
		try {
			Client oldClient = clientRepository.findOne(id);
			String response = "";
			if (oldClient != null) {
				client.setId(oldClient.getId());
				clientRepository.save(client);
				response += "The client was updated with new information";
				return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
			} else {
				response += "The client you want update doesn't exist";
				return new ResponseEntity<String>(response,
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(
					"There was an error updating the client",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * It allows to generate a report of movements of a client inn multiple
	 * accounts
	 * 
	 * @param accountId
	 * @param reportRequest
	 * @return
	 */
	@RequestMapping(value = "/clients/{clientId}/reports", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Report> generateReport(@PathVariable Long clientId,
			@Valid @RequestBody ReportRequest reportRequest) {
		Date initDate = reportRequest.getInitDate();
		Date endDate = reportRequest.getEndDate();
		System.out.println("-------------------------------------------");
		System.out.println(initDate);
		System.out.println(endDate);
		Report report = reportGenerator.generateAccountsReport(initDate,
				endDate, clientId);
		return new ResponseEntity<Report>(report, HttpStatus.OK);

	}
}
