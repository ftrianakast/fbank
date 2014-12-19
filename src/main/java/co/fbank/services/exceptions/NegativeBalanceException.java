package co.fbank.services.exceptions;

import java.math.BigDecimal;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
@SuppressWarnings("serial")
public class NegativeBalanceException extends Exception {
	private BigDecimal negativeBalance;
	private String specificMessage;
	private static final String message = "The balance produced by the movement is negative";

	public NegativeBalanceException(BigDecimal negativeBalance) {
		super(message);
		this.negativeBalance = negativeBalance;
		this.specificMessage = message + " with a balance of: "
				+ this.negativeBalance;

	}

	public BigDecimal getNegativeBalance() {
		return negativeBalance;
	}

	public void setNegativeBalance(BigDecimal negativeBalance) {
		this.negativeBalance = negativeBalance;
	}

	public String getSpecificMessage() {
		return specificMessage;
	}

	public void setSpecificMessage(String specificMessage) {
		this.specificMessage = specificMessage;
	}
}
