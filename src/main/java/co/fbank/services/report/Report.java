package co.fbank.services.report;

import java.util.Date;
import java.util.List;

/**
 * Represents the summary report
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class Report {
	private Date initDate;
	private Date endDate;
	private List<AccountInfo> accounts;

	/**
	 * @param initDate
	 * @param endDate
	 * @param totalCreditMovements
	 * @param totalDebitMovements
	 * @param accounts
	 */
	public Report(Date initDate, Date endDate, List<AccountInfo> accounts) {
		super();
		this.initDate = initDate;
		this.endDate = endDate;
		this.accounts = accounts;
	}

	public Report() {

	}

	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<AccountInfo> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountInfo> accounts) {
		this.accounts = accounts;
	}
}
