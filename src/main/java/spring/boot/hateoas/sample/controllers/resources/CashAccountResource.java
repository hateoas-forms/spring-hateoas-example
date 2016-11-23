package spring.boot.hateoas.sample.controllers.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Resource;

import spring.boot.hateoas.sample.beans.CashAccount;
import spring.boot.hateoas.sample.controllers.CashAccountController;

public class CashAccountResource extends Resource<CashAccount> {

	public CashAccountResource(final CashAccount cashAccount) {
		super(cashAccount);
		add(linkTo(methodOn(CashAccountController.class).get(cashAccount.getNumber())).withSelfRel());
	}

}
