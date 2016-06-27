/**
 * Copyright 2005-2014 hdiv.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hdiv.spring.boot.hateoas.sample;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.escalon.hypermedia.spring.halforms.HalFormsMessageConverter;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableWebMvc
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class SampleHdivApplication {

	@Autowired
	private RelProvider relProvider;

	@Autowired
	private MessageSourceAccessor resourceDescriptionMessageSourceAccessor;

	@Autowired
	private MessageSource messageSource;

	public static void main(final String[] args) {
		SpringApplication.run(SampleHdivApplication.class, args);
	}

	@Bean
	public WebMvcConfigurerAdapter webConfig() {
		return new WebMvcConfigurerAdapter() {
			@Bean
			public HalHttpMessageConverter halMessageConverter() {
				return new HalHttpMessageConverter(messageSource);
			}

			@Bean
			public HalFormsMessageConverter halFormsMessageConverter() {
				HalFormsMessageConverter converter = new HalFormsMessageConverter(objectMapper(), relProvider, curieProvider(),
						resourceDescriptionMessageSourceAccessor);
				converter.setSupportedMediaTypes(Arrays.asList(MediaType.parseMediaType("application/prs.hal-forms+json")));
				return converter;
			}

			@Override
			public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
				converters.add(halMessageConverter());
				converters.add(halFormsMessageConverter());
				super.configureMessageConverters(converters);
			}
		};
	}

	@Bean
	public CurieProvider curieProvider() {
		return new DefaultCurieProvider("halforms", new UriTemplate("/doc/{rel}"));
	}

	@Bean
	public ConversionService defaultConversionService() {
		return new DefaultFormattingConversionService();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
