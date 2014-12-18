package co.fbank.core;

import java.util.ArrayList;

/**
 * 
 * @author felipe
 *
 */
public class Client {
	private String name;
	private String address;
	private String phone;
	private ArrayList<Account> accounts;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param address
	 * @param phone
	 * @param accounts
	 */
	public Client(String name, String address, String phone,
			ArrayList<Account> accounts) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.accounts = accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
}
