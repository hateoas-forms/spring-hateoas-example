package org.hdiv.spring.boot.hateoas.sample.controllers;

import static de.escalon.hypermedia.spring.AffordanceBuilder.linkTo;
import static de.escalon.hypermedia.spring.AffordanceBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.hdiv.spring.boot.hateoas.sample.beans.CashAccount;
import org.hdiv.spring.boot.hateoas.sample.controllers.resources.CashAccountResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Returns the cash accounts to be used in Transfers, in this example no other operation can be done on Accounts
 * @author anderruiz
 *
 */
@RestController
@RequestMapping("/api/cashaccounts")
public class CashAccountController {

	List<CashAccount> cashAccounts = new ArrayList<CashAccount>();

	public CashAccountController() {
		cashAccounts.add(new CashAccount("1111201202332", "Cash Account 1", 1250.3));
		cashAccounts.add(new CashAccount("2222230102332", "Cash Account 2", 250.3));
		cashAccounts.add(new CashAccount("3333299999332", "Cash Account 3", 7250.3));
		cashAccounts.add(new CashAccount("5555501202332", "Cash Account 4", 5250.9));
	}

	/**
	 * Get one particular cashAccount
	 * @param number
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public Resources<CashAccountResource> list() {
		List<CashAccountResource> cashResources = new ArrayList<>();
		for (CashAccount cashAcc : cashAccounts) {
			cashResources.add(new CashAccountResource(cashAcc));
		}
		return new Resources<CashAccountResource>(cashResources, linkTo(methodOn(CashAccountController.class).list()).withSelfRel());
	}

	/**
	 * Get one particular cashAccount
	 * @param number
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, params = "number")
	public Resource<CashAccount> get(final String number) {
		for (CashAccount cashAcc : cashAccounts) {
			if (cashAcc.getNumber().equals(number)) {
				return new CashAccountResource(cashAcc);
			}
		}
		return null;
	}

	/**
	 * Search accounts by number
	 * @param filter
	 * @return
	 */
	@RequestMapping(value = "filter", method = RequestMethod.GET, params = "filter")
	public Resources<CashAccountResource> search(@RequestParam final String filter) {
		List<CashAccountResource> resources = new ArrayList<CashAccountResource>();
		for (CashAccount cashAcc : cashAccounts) {
			if (cashAcc.getNumber().contains(filter)) {
				resources.add(new CashAccountResource(cashAcc));
			}
		}
		return new Resources<CashAccountResource>(resources, linkTo(methodOn(CashAccountController.class).search(filter)).withSelfRel());
	}

	public List<CashAccount> getCashAccounts() {
		return cashAccounts;
	}

}
