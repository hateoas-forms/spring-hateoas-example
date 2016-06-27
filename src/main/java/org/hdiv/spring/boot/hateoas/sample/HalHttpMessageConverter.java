package org.hdiv.spring.boot.hateoas.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class HalHttpMessageConverter extends AbstractJackson2HttpMessageConverter {

	@Autowired
	public HalHttpMessageConverter(MessageSource messageSource) {
		super(new ObjectMapper(), new MediaType("application", "hal+json", DEFAULT_CHARSET));
		objectMapper.registerModule(new Jackson2HalModule());
		objectMapper.setHandlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(new EvoInflectorRelProvider(),
				null, new MessageSourceAccessor(messageSource)));
		// customize your mapper if needed
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return ResourceSupport.class.isAssignableFrom(clazz);
	}

}
