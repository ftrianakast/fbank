package co.fbank.controllers.aux;

import java.util.Date;

/**
 * Represents an object request for the generation of a report
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class ReportRequest {
	private Date initDate;
	private Date endDate;
	private Long clientId;

	/**
	 * Constructor
	 * 
	 * @param initDate
	 * @param endDate
	 * @param clientId
	 */
	public ReportRequest(Date initDate, Date endDate, Long clientId) {
		super();
		this.initDate = initDate;
		this.endDate = endDate;
		this.clientId = clientId;
	}

	public ReportRequest() {
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

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
