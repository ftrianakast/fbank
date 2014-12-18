package co.fbank.core;

import java.util.Date;

/**
 * 
 * @author felipe
 *
 */
public class Movement {

	public MovementType type;
	public Date date;
	public Double value;

	/**
	 * Constructor
	 * @param type
	 * @param date
	 * @param value
	 */
	public Movement(MovementType type, Date date, Double value) {
		super();
		this.type = type;
		this.date = date;
		this.value = value;
	}

	public MovementType getType() {
		return type;
	}

	public void setType(MovementType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

}
