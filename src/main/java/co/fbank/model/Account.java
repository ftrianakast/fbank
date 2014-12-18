package co.fbank.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Felipe Triana
 * @version 1.0
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long number;

	@Column
	private Double balance;

	@OneToMany(fetch = FetchType.LAZY)
	private List<Movement> movements;

	/**
	 * Constructor
	 * 
	 * @param number
	 * @param balance
	 * @param movements
	 */
	public Account(Long number, Double balance, List<Movement> movements) {
		super();
		this.number = number;
		this.balance = balance;
		this.movements = movements;
	}

	public Account(Double balance, List<Movement> movements) {
		super();
		this.balance = balance;
		this.movements = movements;
	}

	/**
	 * JPA required constructor
	 */
	protected Account() {
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<Movement> getMovements() {
		return movements;
	}

	public void setMovements(ArrayList<Movement> movements) {
		this.movements = movements;
	}

	@Override
	public String toString() {
		String strRepresentation = "";
		strRepresentation += "The account with number " + this.getNumber()
				+ " has a balance of " + this.getBalance() + " and "
				+ this.getMovements().size() + " movements";
		return strRepresentation;
	}
}
