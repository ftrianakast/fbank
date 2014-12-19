package co.fbank.services.report;

import java.util.Date;

/**
 * Allows generate reports
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public interface ReportGenerator {
	public Report generateAccountsReport(Date initDate, Date endDate, Long clientId);
}
