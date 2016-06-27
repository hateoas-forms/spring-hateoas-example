package org.hdiv.spring.boot.hateoas.sample.hateoas;

import static de.escalon.hypermedia.spring.AffordanceBuilder.linkTo;
import static de.escalon.hypermedia.spring.AffordanceBuilder.methodOn;

import org.hdiv.spring.boot.hateoas.sample.beans.Transfer;
import org.hdiv.spring.boot.hateoas.sample.controllers.TransferController;
import org.springframework.hateoas.Resource;

import de.escalon.hypermedia.spring.AffordanceBuilder;

public class TransferResource extends Resource<Transfer> {

	public TransferResource(final Transfer transfer) {
		super(transfer);

		add(linkTo(methodOn(TransferController.class).get(transfer.getId())).withSelfRel());
		AffordanceBuilder editTransferBuilder = linkTo(
				methodOn(TransferController.class).modifyTransfer(transfer.getId(), transfer, null, null));
		add(editTransferBuilder.withRel("modify"));
		AffordanceBuilder deleteTransferBuilder = linkTo(methodOn(TransferController.class).deleteTransfer(transfer.getId(), null));
		add(deleteTransferBuilder.withRel("delete"));

	}

}
