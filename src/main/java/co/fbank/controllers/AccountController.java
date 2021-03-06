package co.fbank.controllers;

import java.util.ArrayList;

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

import co.fbank.controllers.aux.Message;
import co.fbank.controllers.aux.NewAccountRequest;
import co.fbank.domain.Account;
import co.fbank.domain.Client;
import co.fbank.domain.Movement;
import co.fbank.persistence.AccountRepository;
import co.fbank.persistence.ClientRepository;

/**
 * @author Felipe Triana
 * @version 1.0
 */
@Controller
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ClientRepository clientRepository;

	/**
	 * 
	 * @param clientId
	 * @return
	 */
	@RequestMapping(value = "/clients/{clientId}/accounts", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Message> addAccount(@PathVariable Long clientId,
			@Valid @RequestBody NewAccountRequest accountRequest) {
		try {
			Client searchedClient = clientRepository.findOne(clientId);
			if (searchedClient != null) {
				Account account = new Account(accountRequest.getInitBalance(),
						new ArrayList<Movement>(), searchedClient);
				searchedClient.getAccounts().add(account);
				accountRepository.save(account);
				return new ResponseEntity<Message>(new Message(
						"The account was added correctly with a number: "
								+ account.getNumber()), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Message>(
						new Message(
								"You need to specify a valid client for add an account"),
						HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Message>(new Message(
					"There was an error adding the account" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 
	 * @param id
	 *            Account Id
	 * @return
	 */
	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Account getAccount(@PathVariable Long id) {
		Account searchedAccount = accountRepository.findOne(id);
		return searchedAccount;
	}

	/**
	 * 
	 * @param id
	 *            Account Id
	 * @return
	 */
	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Message> deleteAccount(@PathVariable Long id) {
		try {
			accountRepository.delete(id);
			return new ResponseEntity<Message>(new Message(
					"The account was successfully removed"),
					HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<Message>(
					new Message(
							"There was an error removing the account. Verify the account number, probably it doesn't exist"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
