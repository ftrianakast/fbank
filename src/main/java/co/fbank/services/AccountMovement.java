package co.fbank.services;

import co.fbank.domain.Account;
import co.fbank.domain.Movement;
import co.fbank.services.exceptions.MovementTypeNotAllowedException;
import co.fbank.services.exceptions.NegativeBalanceException;

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
