package org.hdiv.spring.boot.hateoas.sample.controllers;

import static de.escalon.hypermedia.spring.AffordanceBuilder.linkTo;
import static de.escalon.hypermedia.spring.AffordanceBuilder.methodOn;

import java.util.Date;

import org.hdiv.spring.boot.hateoas.sample.controllers.TransferController.Operations;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.spring.AffordanceBuilder;

@RestController
@RequestMapping("/doc")
public class DocController {

	/**
	 * Returns HAL-FORMS documents Affordances
	 * @param rel
	 * @param principal
	 * @return
	 */
	@RequestMapping(path = "{rel}", method = RequestMethod.GET, produces = "application/prs.hal-forms+json")
	public ResourceSupport get(@PathVariable final String rel) {
		ResourceSupport resourceSupport = new ResourceSupport();
		AffordanceBuilder builder = linkTo(methodOn(DocController.class).get(rel));
		if (Operations.LIST_AFTER_DATE_TRANSFERS.equals(rel)) {
			builder.and(linkTo(methodOn(TransferController.class).getFiltered((Date) null, (Date) null, null)));
		}
		else if (Operations.MODIFY.equals(rel) || Operations.DELETE.equals(rel)) {
			AffordanceBuilder getByIdBuilder = linkTo(methodOn(TransferController.class).get(0));
			AffordanceBuilder editTransferBuilder = linkTo(methodOn(TransferController.class).modifyTransfer(0, null, null));
			AffordanceBuilder deleteTransferBuilder = linkTo(methodOn(TransferController.class).deleteTransfer(0));
			builder.and(getByIdBuilder).and(editTransferBuilder).and(deleteTransferBuilder);

		}
		else if (Operations.MAKE_TRANSFER.equals(rel)) {
			AffordanceBuilder getByIdBuilder = linkTo(methodOn(TransferController.class).get());
			AffordanceBuilder transferBuilder = linkTo(methodOn(TransferController.class).transfer(null, null));
			builder.and(getByIdBuilder).and(transferBuilder);
		}
		resourceSupport.add(builder.withSelfRel());
		return resourceSupport;
	}

}
