package co.fbank.controllers.aux;

import java.math.BigDecimal;

/**
 * Reques for a new account
 * @author Felipe Triana
 *
 */
public class NewAccountRequest {
	private BigDecimal initBalance;

	public NewAccountRequest(BigDecimal initBalance) {
		super();
		this.initBalance = initBalance;
	}
	
	public NewAccountRequest(){
		
	}
	
	public BigDecimal getInitBalance() {
		return initBalance;
	}

	public void setInitBalance(BigDecimal initBalance) {
		this.initBalance = initBalance;
	}
}
