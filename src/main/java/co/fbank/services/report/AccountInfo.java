package co.fbank.services.report;

import java.util.List;

/**
 * Represent the individual account info in a report
 * 
 * @author Felipe Triana
 * @version
 */
public class AccountInfo {

	private Long accountNumber;
	private Integer totalDebitMovements;
	private Integer totalCreditMovements;
	private List<MovementInfo> debitMovements;
	private List<MovementInfo> creditMovements;

	/**
	 * Constructor
	 * 
	 * @param accountNumber
	 * @param totalDebitMovements
	 * @param totalCreditMovements
	 * @param debitMovements
	 * @param creditMovements
	 */
	public AccountInfo(Long accountNumber, Integer totalDebitMovements,
			Integer totalCreditMovements, List<MovementInfo> debitMovements,
			List<MovementInfo> creditMovements) {
		super();
		this.accountNumber = accountNumber;
		this.totalDebitMovements = totalDebitMovements;
		this.totalCreditMovements = totalCreditMovements;
		this.debitMovements = debitMovements;
		this.creditMovements = creditMovements;
	}

	public AccountInfo() {
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getTotalDebitMovements() {
		return totalDebitMovements;
	}

	public void setTotalDebitMovements(Integer totalDebitMovements) {
		this.totalDebitMovements = totalDebitMovements;
	}

	public Integer getTotalCreditMovements() {
		return totalCreditMovements;
	}

	public void setTotalCreditMovements(Integer totalCreditMovements) {
		this.totalCreditMovements = totalCreditMovements;
	}

	public List<MovementInfo> getDebitMovements() {
		return debitMovements;
	}

	public void setDebitMovements(List<MovementInfo> debitMovements) {
		this.debitMovements = debitMovements;
	}

	public List<MovementInfo> getCreditMovements() {
		return creditMovements;
	}

	public void setCreditMovements(List<MovementInfo> creditMovements) {
		this.creditMovements = creditMovements;
	}
}
