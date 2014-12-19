package co.fbank.services;

import org.springframework.stereotype.Service;

import co.fbank.model.MovementType;

@Service
public class BalanceValidatorImpl implements BalanceValidator {

	@Override
	public Boolean isNegativeBalanceGivenNewMovement(Double balance,
			Double transferedMoney, MovementType mvmType) {
		Boolean negativeBalance = false;
		if (mvmType.equals(MovementType.CREDIT)
				&& (balance - transferedMoney) < 0) {
			negativeBalance = true;
		}
		return negativeBalance;
	}

}
