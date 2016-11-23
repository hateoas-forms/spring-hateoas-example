package spring.boot.hateoas.sample.controllers;

import static com.github.hateoas.forms.spring.AffordanceBuilder.linkTo;
import static com.github.hateoas.forms.spring.AffordanceBuilder.methodOn;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.hateoas.forms.spring.AffordanceBuilder;

import spring.boot.hateoas.sample.controllers.TransferController.Operations;

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
		else if (Operations.MODIFY.equals(rel)) {
			builder.and(linkTo(methodOn(TransferController.class).modifyTransfer(0, null, null)));
		}
		else if (Operations.DELETE.equals(rel)) {
			builder.and(linkTo(methodOn(TransferController.class).deleteTransfer(0)));
		}
		else if (Operations.MAKE_TRANSFER.equals(rel)) {
			builder.and(linkTo(methodOn(TransferController.class).transfer(null, null)));
		}
		resourceSupport.add(builder.withSelfRel());
		return resourceSupport;
	}

}
