package co.fbank.services.report;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import co.fbank.services.utils.JsonDateSerializer;

/**
 * Represents the movement info in a report
 * 
 * @author Felipe Triana
 * @version 1.0
 *
 */
public class MovementInfo {

	private BigDecimal value;
	private Date date;

	public MovementInfo(BigDecimal value, Date date) {
		super();
		this.value = value;
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
