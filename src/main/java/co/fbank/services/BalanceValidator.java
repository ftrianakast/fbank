package co.fbank.services;

import co.fbank.model.MovementType;

public interface BalanceValidator {

	/**
	 * Validate if the actual movement has a negative effect on an account. This
	 * case is only presented when the movement type is CREDIT
	 * 
	 * @param balance
	 * @param transferedMoney
	 * @param mvmType
	 * @return
	 */
	public Boolean isNegativeBalanceGivenNewMovement(Double balance,
			Double transferedMoney, MovementType mvmType);
}
