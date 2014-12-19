package co.fbank.services.report;

import java.math.BigDecimal;
import java.util.Date;

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
	private BigDecimal balance;

	public MovementInfo(BigDecimal value, Date date, BigDecimal balance) {
		super();
		this.value = value;
		this.date = date;
		this.balance = balance;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
