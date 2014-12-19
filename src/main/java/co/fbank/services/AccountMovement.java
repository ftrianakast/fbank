package co.fbank.services;

import co.fbank.model.Account;
import co.fbank.model.Movement;
import co.fbank.utils.exceptions.MovementTypeNotAllowedException;
import co.fbank.utils.exceptions.NegativeBalanceException;

/**
 * Service that performs a transfer of money into|from an account
 * 
 * @author felipe
 *
 */
public interface AccountMovement {
	/**
	 * Register a movement (a transfer or substraction of money) into|from an
	 * account
	 * 
	 * @param movement
	 * @param account
	 */
	public void registerMovement(Movement movement, Account account)
			throws NegativeBalanceException, MovementTypeNotAllowedException;
}
