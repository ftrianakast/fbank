package co.fbank.services.utils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Felipe Triana
 * @version 1.0
 */
public class Constants {

	public static final List<String> allowedMovementTypes = Arrays.asList(
			"credit", "CREDIT", "debit", "DEBIT");

	public static final List<String> CREDIT_REPRESENTATIONS = Arrays.asList(
			"credit", "CREDIT");

	public static final List<String> DEBIT_REPRESENTATIONS = Arrays.asList(
			"debit", "DEBIT");

}
