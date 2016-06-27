package org.hdiv.spring.boot.hateoas.sample.controllers;

import static de.escalon.hypermedia.spring.AffordanceBuilder.linkTo;
import static de.escalon.hypermedia.spring.AffordanceBuilder.methodOn;

import java.security.Principal;
import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.escalon.hypermedia.spring.AffordanceBuilder;

@RestController
@RequestMapping("/doc")
public class DocController {

	@RequestMapping(path = "{rel}", method = RequestMethod.GET, produces = "application/prs.hal-forms+json")
	public ResourceSupport get(@PathVariable final String rel, final Principal principal) {
		ResourceSupport resourceSupport = new ResourceSupport();
		AffordanceBuilder builder = linkTo(methodOn(DocController.class).get(rel, principal));
		if ("list-after-date-transfers".equals(rel)) {
			builder.and(linkTo(methodOn(TransferController.class).getFiltered((Date) null, (Date) null, null)));
		}
		else if ("modify".equals(rel) || "delete".equals(rel)) {
			AffordanceBuilder getByIdBuilder = linkTo(methodOn(TransferController.class).get(0));
			AffordanceBuilder editTransferBuilder = linkTo(methodOn(TransferController.class).modifyTransfer(0, null, null, null));
			AffordanceBuilder deleteTransferBuilder = linkTo(methodOn(TransferController.class).deleteTransfer(0, null));
			builder.and(getByIdBuilder).and(editTransferBuilder).and(deleteTransferBuilder);

		}
		else if ("make-transfer".equals(rel)) {
			AffordanceBuilder getByIdBuilder = linkTo(methodOn(TransferController.class).get());
			AffordanceBuilder transferBuilder = linkTo(methodOn(TransferController.class).transfer(null, null));
			builder.and(getByIdBuilder).and(transferBuilder);
		}
		resourceSupport.add(builder.withSelfRel());
		return resourceSupport;
	}

}
