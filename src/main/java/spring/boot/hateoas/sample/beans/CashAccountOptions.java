package spring.boot.hateoas.sample.beans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.hateoas.forms.action.Options;
import com.github.hateoas.forms.affordance.Suggest;
import com.github.hateoas.forms.affordance.SuggestImpl;

import spring.boot.hateoas.sample.controllers.CashAccountController;

@Component
public class CashAccountOptions implements Options<CashAccount> {

	@Autowired
	private CashAccountController controller;

	@Override
	public List<Suggest<CashAccount>> get(final String[] value, final Object... args) {
		return SuggestImpl.wrap(controller.getCashAccounts(), "number", "description");
	}

}
