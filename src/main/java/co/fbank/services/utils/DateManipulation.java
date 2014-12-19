package co.fbank.services.utils;

import java.util.Date;

/**
 * Allows comparation and manipulation of dates
 * 
 * @author Felipe Triana
 * @version 1.0
 */
public interface DateManipulation {
	public Boolean isDateBetween(Date actualDate, Date initDate, Date endDate);
}
