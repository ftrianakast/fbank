package co.fbank.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import co.fbank.model.MovementType;

@Service
public class BalanceValidatorImpl implements BalanceValidator {

	@Override
	public Boolean isNegativeBalanceGivenNewMovement(BigDecimal balance,
			BigDecimal transferedMoney, MovementType mvmType) {
		Boolean negativeBalance = false;
		if (mvmType.equals(MovementType.CREDIT)
				&& (balance.subtract(transferedMoney))
						.compareTo(new BigDecimal(0)) == -1) {
			negativeBalance = true;
		}
		return negativeBalance;
	}

}
