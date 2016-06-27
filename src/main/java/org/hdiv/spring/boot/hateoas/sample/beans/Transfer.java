package org.hdiv.spring.boot.hateoas.sample.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.escalon.hypermedia.action.Input;
import de.escalon.hypermedia.action.Select;
import de.escalon.hypermedia.action.Type;

public class Transfer {

	private final Integer id;

	private final String fromAccount;

	private final String toAccount;

	private final String description;

	private double amount;

	private Date date;

	private final TransferType type;

	private final TransferStatus status;

	private final String email;

	@JsonCreator
	public Transfer(@JsonProperty @Input(value = Type.HIDDEN, editable = false) final Integer id,
			@JsonProperty @Select(options = CashAccountOptions.class) final String fromAccount,
			@JsonProperty @Select(options = CashAccountFilteredOptions.class) final String toAccount,
			@JsonProperty @Input(editable = true, required = true) final String description,
			@JsonProperty @Input(editable = true, required = true) final double amount, @JsonProperty final Date date,
			@JsonProperty final TransferType type, @JsonProperty final TransferStatus status,
			@JsonProperty @Input(value = Type.EMAIL) final String email) {
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

	public void setAmount(final double amount) {
		this.amount = amount;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}
