package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.exception.WebClientException;
import com.example.demo.filter.WebClientFilter;
import com.example.demo.util.EncryptDecryptUtil;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@SpringBootApplication
public class UserProfileManagementApplication {

	@Value("${base.url}")
	public String baseUrl;

	@Value("${okta.api.token}")
	public String oktaApiTokenEncrypted;

	@Autowired
	EncryptDecryptUtil EncryptDecryptUtil;

	@Bean(name = "oktaWebClient")
	public WebClient webClient() {
		HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000).doOnConnected(
				conn -> conn.addHandlerLast(new ReadTimeoutHandler(10)).addHandlerLast(new WriteTimeoutHandler(10)));
		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
		return WebClient.builder().baseUrl(baseUrl).clientConnector(connector)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//				.defaultHeader(HttpHeaders.AUTHORIZATION, EncryptDecryptUtil.decrypt(oktaApiTokenEncrypted))
				.defaultHeader(HttpHeaders.AUTHORIZATION, oktaApiTokenEncrypted)
				.filter(WebClientFilter.logRequest())
				.filter(WebClientFilter.logResponse())
				.filter(WebClientException.handleError())
				.build();
	}
	
	@Bean
	public MessageSource messageSource() {
	 ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//	    messageSource.setBasename("classpath:external-properties-for-user-bean-error");
	 messageSource.setBasename("file:C:/MyFiles/Projects/properties/external-properties-for-user-bean-error");
	 messageSource.setDefaultEncoding("UTF-8");
	 return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	 LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	 bean.setValidationMessageSource(messageSource());
	 return bean;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(UserProfileManagementApplication.class, args);
	}
}