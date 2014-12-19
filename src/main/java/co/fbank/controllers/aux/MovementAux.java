package co.fbank.controllers.aux;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import co.fbank.domain.Movement;
import co.fbank.domain.MovementType;
import co.fbank.services.utils.Constants;

/**
 * Defines an auxiliar object for building real movements form client requests
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class MovementAux {

	private String type;
	private BigDecimal value;

	/**
	 * 
	 * @param type
	 * @param value
	 */
	public MovementAux(String type, BigDecimal value) {
		super();
		this.type = type;
		this.value = value;
	}

	protected MovementAux() {

	}

	/**
	 * It performs transformation from an input client movement to a domain
	 * movement object
	 * 
	 * @return
	 */
	public Optional<Movement> getRealMovement() {
		Optional<String> credit = Constants.CREDIT_REPRESENTATIONS.stream()
				.filter(x -> x.equals(getType())).findFirst();
		Optional<String> debit = Constants.DEBIT_REPRESENTATIONS.stream()
				.filter(x -> x.equals(getType())).findFirst();
		Movement realMovement;
		if (credit.isPresent()) {
			realMovement = new Movement(MovementType.CREDIT, new Date(),
					getValue());
			return Optional.of(realMovement);
		} else if (debit.isPresent()) {
			realMovement = new Movement(MovementType.DEBIT, new Date(),
					getValue());
			return Optional.of(realMovement);
		} else {
			return Optional.empty();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
}
