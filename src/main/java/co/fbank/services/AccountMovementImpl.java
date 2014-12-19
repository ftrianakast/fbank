package co.fbank.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.fbank.model.Account;
import co.fbank.model.Movement;
import co.fbank.model.MovementType;
import co.fbank.persistence.AccountRepository;
import co.fbank.persistence.MovementRepository;
import co.fbank.utils.exceptions.MovementTypeNotAllowedException;
import co.fbank.utils.exceptions.NegativeBalanceException;

@Service
public class AccountMovementImpl implements AccountMovement {

	@Autowired
	private BalanceValidator balanceValidator;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MovementRepository movementRepository;

	@Override
	public void registerMovement(Movement movement, Account account)
			throws NegativeBalanceException, MovementTypeNotAllowedException {
		Double accountBalance = account.getBalance();
		Double mvmValue = movement.getValue();
		MovementType mvmType = movement.getType();
		if (!balanceValidator.isNegativeBalanceGivenNewMovement(accountBalance,
				mvmValue, mvmType)) {
			movement.setAccount(account);
			movement.setDate(new Date());
			switch (mvmType) {
			case CREDIT:
				account.setBalance(accountBalance - mvmValue);
				performMovementTransaction(movement, account);
				break;
			case DEBIT:
				System.out.println("entre por acá");
				account.setBalance(accountBalance + mvmValue);
				performMovementTransaction(movement, account);
				break;
			default:
				throw new MovementTypeNotAllowedException();
			}
		} else {
			throw new NegativeBalanceException(accountBalance - mvmValue);
		}
	}

	@Transactional
	private void performMovementTransaction(Movement movement, Account account) {
		movementRepository.save(movement);
		accountRepository.save(account);
	}

}
