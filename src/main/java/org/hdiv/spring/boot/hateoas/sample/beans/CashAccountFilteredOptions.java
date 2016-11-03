package org.hdiv.spring.boot.hateoas.sample.beans;

import java.util.Arrays;
import java.util.List;

import org.hdiv.spring.boot.hateoas.sample.controllers.CashAccountController;
import org.springframework.hateoas.Link;

import com.github.hateoas.forms.action.Options;
import com.github.hateoas.forms.affordance.Suggest;
import com.github.hateoas.forms.affordance.SuggestImpl;
import com.github.hateoas.forms.spring.AffordanceBuilder;

public class CashAccountFilteredOptions implements Options<String> {
	@Override
	public List<Suggest<String>> get(final String[] value, final Object... args) {
		Link link = AffordanceBuilder.linkTo(AffordanceBuilder.methodOn(CashAccountController.class).search(null)).withSelfRel();
		return SuggestImpl.wrap(Arrays.asList(link.getHref()), "number", "description");
	}
}
