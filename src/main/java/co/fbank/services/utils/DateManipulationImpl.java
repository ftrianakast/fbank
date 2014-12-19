package co.fbank.services.utils;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateManipulationImpl implements DateManipulation {

	@Override
	public Boolean isDateBetween(Date actualDate, Date initDate, Date endDate) {
		return actualDate.compareTo(initDate) >= 0
				&& actualDate.compareTo(endDate) <= 0;
	}

}
