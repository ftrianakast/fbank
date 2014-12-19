package co.fbank.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Felipe Triana
 * @version 1.0
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "accountNumber")
	private Long number;

	@Column
	private BigDecimal balance;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy= "account")
	@JsonManagedReference
	private List<Movement> movements;

	@ManyToOne
	@JoinColumn(name = "clientId")
	@JsonBackReference
	private Client client;

	/**
	 * Constructor
	 * 
	 * @param number
	 * @param balance
	 * @param movements
	 */
	public Account(Long number, BigDecimal balance, List<Movement> movements,
			Client client) {
		super();
		this.number = number;
		this.balance = balance;
		this.movements = movements;
		this.client = client;
	}

	public Account(BigDecimal balance, List<Movement> movements, Client client) {
		super();
		this.balance = balance;
		this.movements = movements;
		this.client = client;
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Movement> getMovements() {
		return movements;
	}

	public void setMovements(List<Movement> movements) {
		this.movements = movements;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
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
