package co.fbank.controllers.aux;

/**
 * It is a message to the client
 * @author Felipe Triana
 *
 */
public class Message {
	private String message;

	public Message(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
