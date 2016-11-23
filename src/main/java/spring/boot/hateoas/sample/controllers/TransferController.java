package spring.boot.hateoas.sample.controllers;

import static com.github.hateoas.forms.spring.AffordanceBuilder.linkTo;
import static com.github.hateoas.forms.spring.AffordanceBuilder.methodOn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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

import com.github.hateoas.forms.action.Input;
import com.github.hateoas.forms.action.Type;

import spring.boot.hateoas.sample.beans.Transfer;
import spring.boot.hateoas.sample.beans.TransferStatus;
import spring.boot.hateoas.sample.beans.TransferType;
import spring.boot.hateoas.sample.controllers.resources.TransferResource;

@RestController
@RequestMapping(value = "/api/transfer")
public class TransferController {

	List<Transfer> transfers = new ArrayList<>();

	public TransferController() {
		transfers.add(new Transfer(1, "1111201202332", "3333299999332", "Transfer1", 12.3, new Date(0), TransferType.INTERNATIONAL,
				TransferStatus.COMPLETED, "ander@dummy.com"));
		transfers.add(new Transfer(2, "3333299999332", "1111201202332", "Transfer2", 15.3, new Date(1000), TransferType.NATIONAL,
				TransferStatus.PENDING, "ander@dummy.com"));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Resource<Transfer>> transfer(@Valid @RequestBody final Transfer transfer, final BindingResult bindingResult) {

		transfer.setDate(new Date());

		transfers.add(transfer);

		Resource<Transfer> resource = new Resource<Transfer>(transfer);
		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		return new ResponseEntity<Resource<Transfer>>(resource, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Transfer>> modifyTransfer(@PathVariable("id") final Integer id,
			@Valid @RequestBody final Transfer transfer, final BindingResult bindingResult) {

		transfer.setDate(new Date());

		transfers.remove(findById(transfer.getId()));
		transfers.add(transfer);

		Resource<Transfer> resource = new Resource<Transfer>(transfer);
		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		return new ResponseEntity<Resource<Transfer>>(resource, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteTransfer(@PathVariable("id") final Integer id) {

		transfers.remove(findById(id));

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Resource<Transfer> get(@Input(required = true, pattern = "^[1-9]\\d*$") @PathVariable("id") final Integer id) {
		Transfer transfer = findById(id);
		Resource<Transfer> resource = new TransferResource(transfer);
		resource.add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
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
						.withRel(Operations.LIST_AFTER_DATE_TRANSFERS.toRel()));
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
				linkTo(methodOn(TransferController.class).getFiltered(dateFrom, dateTo, status))
						.withRel(Operations.LIST_AFTER_DATE_TRANSFERS.toRel()));
	}

	private Transfer findById(final int id) {
		for (Transfer transfer : transfers) {
			if (transfer.getId().equals(id)) {
				return transfer;
			}
		}
		return null;
	}

	public enum Operations {
		LIST_AFTER_DATE_TRANSFERS, MODIFY, DELETE, MAKE_TRANSFER;

		public String toRel() {
			return name().toLowerCase().replace("_", "-");
		}

		public boolean equals(final String rel) {
			return toRel().equals(rel);
		}
	}

}
