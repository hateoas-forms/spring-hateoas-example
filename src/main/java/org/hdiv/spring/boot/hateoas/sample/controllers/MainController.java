package org.hdiv.spring.boot.hateoas.sample.controllers;

import static de.escalon.hypermedia.spring.AffordanceBuilder.linkTo;
import static de.escalon.hypermedia.spring.AffordanceBuilder.methodOn;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry point for the API
 * @author anderruiz
 *
 */
@RestController
public class MainController {

	@RequestMapping(value = "/api/")
	public ResponseEntity<ResourceSupport> home(final Principal principal) {
		ResourceSupport resource = new ResourceSupport();
		List<Link> links = new ArrayList<Link>();

		/**
		 * Create a transfer
		 */
		Link linkMakeTransfer = linkTo(methodOn(TransferController.class).transfer(null, null)).withRel("make-transfer");
		links.add(linkMakeTransfer);

		/**
		 * List all transfers
		 */
		Link linkListTransfers = linkTo(methodOn(TransferController.class).get()).withRel("list-transfers");
		links.add(linkListTransfers);

		/**
		 * GET filter for transfers
		 */
		Link linkListAfterDateTransfers = linkTo(methodOn(TransferController.class).getFiltered((Date) null, (Date) null, null))
				.withRel("list-after-date-transfers");
		links.add(linkListAfterDateTransfers);

		resource.add(links);
		ResponseEntity<ResourceSupport> responseEntity = new ResponseEntity<ResourceSupport>(resource, HttpStatus.OK);
		return responseEntity;
	}

}