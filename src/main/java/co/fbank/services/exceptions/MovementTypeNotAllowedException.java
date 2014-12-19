package co.fbank.services.exceptions;

/**
 * 
 * @author Felipe Triana
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MovementTypeNotAllowedException extends Exception {

	/**
	 * Constructor
	 */
	public MovementTypeNotAllowedException() {
		super("Movement type is not allowed");
	}
}
