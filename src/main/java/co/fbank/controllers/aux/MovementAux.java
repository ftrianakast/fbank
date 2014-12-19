package co.fbank.controllers.aux;

import java.util.Date;
import java.util.Optional;

import co.fbank.model.Movement;
import co.fbank.model.MovementType;
import co.fbank.utils.Constants;

/**
 * Defines an auxiliar object for building real movements form client requests
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class MovementAux {

	private String type;
	private Double value;

	/**
	 * 
	 * @param type
	 * @param value
	 */
	public MovementAux(String type, Double value) {
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
