package org.hdiv.spring.boot.hateoas.sample.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.hdiv.spring.boot.hateoas.sample.beans.CashAccount;
import org.hdiv.spring.boot.hateoas.sample.controllers.CashAccountController;
import org.springframework.hateoas.Resource;

public class CashAccountResource extends Resource<CashAccount> {

	public CashAccountResource(final CashAccount cashAccount) {
		super(cashAccount);
		add(linkTo(methodOn(CashAccountController.class).get(cashAccount.getNumber())).withSelfRel());
	}

}
