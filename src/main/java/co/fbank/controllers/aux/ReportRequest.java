package co.fbank.controllers.aux;

import java.util.Date;

import co.fbank.services.utils.JsonDateDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Represents an object request for the generation of a report
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public class ReportRequest {
	private Date initDate;
	private Date endDate;

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
	}

	public ReportRequest() {
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public Date getInitDate() {
		return initDate;
	}

	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}

	@JsonDeserialize(using = JsonDateDeserializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
