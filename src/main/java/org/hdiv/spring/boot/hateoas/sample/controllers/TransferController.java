package org.hdiv.spring.boot.hateoas.sample.controllers;

import static de.escalon.hypermedia.spring.AffordanceBuilder.linkTo;
import static de.escalon.hypermedia.spring.AffordanceBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.hdiv.spring.boot.hateoas.sample.beans.Transfer;
import org.hdiv.spring.boot.hateoas.sample.beans.TransferStatus;
import org.hdiv.spring.boot.hateoas.sample.hateoas.TransferResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.action.Input;
import de.escalon.hypermedia.action.Type;
import de.escalon.hypermedia.spring.AffordanceBuilder;

@RestController
@RequestMapping(value = "/api/transfer")
public class TransferController {

	List<Transfer> transfers = new ArrayList<>();

	public TransferController() {

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Resource<Transfer>> transfer(@Valid @RequestBody final Transfer transfer, final BindingResult bindingResult) {

		transfer.setDate(new Date());

		double amount = transfer.getAmount();
		transfer.setAmount(round(amount, 2));

		transfers.add(transfer);

		Resource<Transfer> resource = new Resource<Transfer>(transfer);
		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		return new ResponseEntity<Resource<Transfer>>(resource, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Transfer>> modifyTransfer(@PathVariable("id") final Integer id,
			@Valid @RequestBody final Transfer transfer, final BindingResult bindingResult, final Principal principal) {

		transfer.setDate(new Date());

		double amount = transfer.getAmount();
		transfer.setAmount(round(amount, 2));

		transfers.remove(findById(transfer.getId()));
		transfers.add(transfer);

		Resource<Transfer> resource = new Resource<Transfer>(transfer);
		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		return new ResponseEntity<Resource<Transfer>>(resource, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTransfer(@PathVariable("id") final Integer id, final Principal principal) {

		transfers.remove(findById(id));

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Resource<Transfer> get(@Input(required = true, pattern = "^[1-9]\\d*$") @PathVariable("id") final Integer id) {
		Transfer transfer = findById(id);

		Resource<Transfer> resource = new TransferResource(transfer);
		AffordanceBuilder editTransferBuilder = linkTo(methodOn(TransferController.class).modifyTransfer(id, transfer, null, null));

		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).and(editTransferBuilder).withSelfRel());
		return resource;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Resources<TransferResource> get() {

		List<TransferResource> resources = new ArrayList<TransferResource>();
		for (Transfer transfer : transfers) {
			resources.add(new TransferResource(transfer));
		}

		return new Resources<TransferResource>(resources, linkTo(methodOn(TransferController.class).get()).withSelfRel(),
				linkTo(methodOn(TransferController.class).getFiltered((Date) null, (Date) null, null))
						.withRel("list-after-date-transfers"));
	}

	@RequestMapping(value = "/filter", method = RequestMethod.GET)
	public Resources<TransferResource> getFiltered(
			@RequestParam(value = "dateFrom", required = false) @Input(value = Type.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date dateFrom,
			@RequestParam(value = "dateTo", required = false) @Input(value = Type.DATE) @DateTimeFormat(pattern = "yyyy-MM-dd") final Date dateTo,
			@RequestParam(value = "status", required = false) final TransferStatus status) {

		List<TransferResource> resources = new ArrayList<TransferResource>();
		for (Transfer transfer : transfers) {
			if (transfer.getDate().after(dateFrom) && transfer.getDate().before(dateTo)) {
				resources.add(new TransferResource(transfer));
			}
		}

		return new Resources<TransferResource>(resources, linkTo(methodOn(TransferController.class).get()).withSelfRel(),
				linkTo(methodOn(TransferController.class).getFiltered(dateFrom, dateTo, status)).withRel("list-after-date-transfers"));
	}

	public static double round(double value, final int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	private Transfer findById(final int id) {
		for (Transfer transfer : transfers) {
			if (transfer.getId().equals(id)) {
				return transfer;
			}
		}
		return null;
	}

}
