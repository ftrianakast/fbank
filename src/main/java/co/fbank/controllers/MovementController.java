package co.fbank.controllers;

import java.util.Optional;

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

import co.fbank.controllers.aux.MovementAux;
import co.fbank.domain.Account;
import co.fbank.domain.Movement;
import co.fbank.persistence.AccountRepository;
import co.fbank.persistence.MovementRepository;
import co.fbank.services.AccountMovement;
import co.fbank.services.exceptions.MovementTypeNotAllowedException;
import co.fbank.services.exceptions.NegativeBalanceException;

/**
 * @author Felipe Triana
 * @version 1.0
 */
@Controller
public class MovementController {

	@Autowired
	private MovementRepository movementRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountMovement accountMovement;

	/**
	 * It register a movement into an account
	 * 
	 * @param accountNumber
	 * @param movement
	 * @return
	 */
	@RequestMapping(value = "/accounts/{accountNumber}/movements", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<String> registerMovement(
			@PathVariable Long accountNumber,
			@Valid @RequestBody MovementAux mvmClientInput) {
		Account account = accountRepository.findOne(accountNumber);
		try {
			Optional<Movement> realMvm = mvmClientInput.getRealMovement();
			if (realMvm.isPresent()) {
				accountMovement.registerMovement(realMvm.get(), account);
				return new ResponseEntity<String>(
						"Your movement was performed and you have a new balance of: "
								+ account.getBalance(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<String>(
						"Your movement type is not allowed",
						HttpStatus.BAD_REQUEST);
			}
		} catch (NegativeBalanceException e) {
			return new ResponseEntity<String>(e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (MovementTypeNotAllowedException e) {
			return new ResponseEntity<String>(e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}
	}

}
