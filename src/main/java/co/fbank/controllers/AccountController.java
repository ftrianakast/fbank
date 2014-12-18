package co.fbank.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import co.fbank.model.Account;
import co.fbank.model.Client;
import co.fbank.model.Movement;
import co.fbank.persistence.AccountRepository;
import co.fbank.persistence.ClientRepository;

@Controller
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ClientRepository clientRepository;

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public String addAccount(@RequestParam(required = true) Long clientId) {
		try {
			Client searchedClient = clientRepository.findOne(clientId);
			if (searchedClient != null) {
				Account account = new Account(0.0,
						new ArrayList<Movement>());
				searchedClient.getAccounts().add(account);
				accountRepository.save(account);
				return "The account was added correctly with a number: "
						+ account.getNumber();
			} else {
				return "You need to specify a valid client for add an account";
			}
		} catch (Exception e) {
			return "There was an error adding the account" + e.getMessage();
		}
	}
}
