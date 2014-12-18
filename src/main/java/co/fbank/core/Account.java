package co.fbank.core;

/**
 * 
 * @author felipe
 *
 */
public class Account {
	public String number;
	public Double balance;

	public Account(String number, Double balance) {
		super();
		this.number = number;
		this.balance = balance;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
