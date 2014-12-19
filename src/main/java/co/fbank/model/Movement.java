package co.fbank.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * @author Felipe Triana
 * @version 1.0
 */
@Entity
public class Movement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private MovementType type;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private Double value;

	@ManyToOne
	@JoinColumn(name = "accountNumber")
	@JsonBackReference
	private Account account;

	/**
	 * Constructor
	 * 
	 * @param type
	 * @param date
	 * @param value
	 */
	public Movement(MovementType type, Date date, Double value) {
		super();
		this.type = type;
		this.date = date;
		this.value = value;
	}

	/**
	 * JPA required constructor
	 */
	protected Movement() {
	}

	public MovementType getType() {
		return type;
	}

	public void setType(MovementType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
