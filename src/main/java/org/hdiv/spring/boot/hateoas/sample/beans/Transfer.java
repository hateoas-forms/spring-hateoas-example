package org.hdiv.spring.boot.hateoas.sample.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;

import de.escalon.hypermedia.action.Input;
import de.escalon.hypermedia.action.Select;
import de.escalon.hypermedia.action.Type;

public class Transfer {

	private Integer id;

	private String fromAccount;

	private String toAccount;

	private String description;

	private double amount;

	private Date date;

	private TransferType type;

	private TransferStatus status;

	private String email;

	public Transfer() {

	}

	@JsonCreator
	public Transfer(final Integer id, final String fromAccount, final String toAccount, final String description, final double amount,
			final Date date, final TransferType type, final TransferStatus status, final String email) {
		this.id = id;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.description = description;
		this.date = date;
		this.type = type;
		this.status = status;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public TransferType getType() {
		return type;
	}

	public TransferStatus getStatus() {
		return status;
	}

	public String getEmail() {
		return email;
	}

	public void setAmount(@Input(editable = true, required = true) final double amount) {
		this.amount = amount;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public void setId(@Input(value = Type.HIDDEN, editable = false) final Integer id) {
		this.id = id;
	}

	public void setFromAccount(@Select(options = CashAccountOptions.class) final String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public void setToAccount(@Select(options = CashAccountFilteredOptions.class) final String toAccount) {
		this.toAccount = toAccount;
	}

	public void setDescription(@Input(editable = true, required = true) final String description) {
		this.description = description;
	}

	public void setType(final TransferType type) {
		this.type = type;
	}

	public void setStatus(final TransferStatus status) {
		this.status = status;
	}

	public void setEmail(@Input(value = Type.EMAIL) final String email) {
		this.email = email;
	}

}
