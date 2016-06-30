package org.hdiv.spring.boot.hateoas.sample.beans;

import java.util.List;

import org.hdiv.spring.boot.hateoas.sample.controllers.CashAccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.escalon.hypermedia.action.Options;
import de.escalon.hypermedia.affordance.Suggest;
import de.escalon.hypermedia.affordance.SuggestImpl;
import de.escalon.hypermedia.affordance.SuggestType;

@Component
public class CashAccountOptions implements Options<CashAccount> {

	@Autowired
	private CashAccountController controller;

	@Override
	public List<Suggest<CashAccount>> get(final SuggestType type, final String[] value, final Object... args) {
		return SuggestImpl.wrap(controller.getCashAccounts(), "number", "description", SuggestType.EXTERNAL);
	}

}
