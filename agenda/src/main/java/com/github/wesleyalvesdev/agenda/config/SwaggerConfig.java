package com.github.wesleyalvesdev.agenda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket agendaApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.github.wesleyalvesdev.agenda"))
				.paths(regex("/api.*"))
				.build()
				.apiInfo(metaInfo());
	}
	
	@SuppressWarnings("rawtypes")
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"Angenda API REST",
				"API REST de agenda de contatos",
				"1.0",
				"Terms of Service",
				new Contact("Wesley Alves", "https://github.com/wesleyAlvesDev",
                        "wesleydrrr@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
                );
		
		return apiInfo;
	}
}
