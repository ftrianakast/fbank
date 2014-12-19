package co.fbank.services;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.fbank.domain.Account;
import co.fbank.domain.Movement;
import co.fbank.domain.MovementType;
import co.fbank.persistence.AccountRepository;
import co.fbank.persistence.MovementRepository;
import co.fbank.services.exceptions.MovementTypeNotAllowedException;
import co.fbank.services.exceptions.NegativeBalanceException;

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
		BigDecimal accountBalance = account.getBalance();
		BigDecimal mvmValue = movement.getValue();
		MovementType mvmType = movement.getType();
		if (!balanceValidator.isNegativeBalanceGivenNewMovement(accountBalance,
				mvmValue, mvmType)) {
			movement.setAccount(account);
			movement.setDate(new Date());
			switch (mvmType) {
			case CREDIT:
				account.setBalance(accountBalance.subtract(mvmValue));
				performMovementTransaction(movement, account);
				break;
			case DEBIT:
				System.out.println("entre por acá");
				account.setBalance(accountBalance.add(mvmValue));
				performMovementTransaction(movement, account);
				break;
			default:
				throw new MovementTypeNotAllowedException();
			}
		} else {
			throw new NegativeBalanceException(accountBalance.subtract(mvmValue));
		}
	}

	@Transactional
	private void performMovementTransaction(Movement movement, Account account) {
		movementRepository.save(movement);
		accountRepository.save(account);
	}

}
