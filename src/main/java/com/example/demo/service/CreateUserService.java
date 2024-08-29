package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.model.CreateUserRequest;
import com.example.demo.model.CreateUserResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * This service will create a user without credentials
 * 
 * @author msivasubbu001
 *
 */

@Component
@Service
@Slf4j
public class CreateUserService {
	@Qualifier("oktaWebClient")
	@Autowired
	WebClient webClient;

	@Value("${create.user.url}")
	public String createUserUrl;

	/**
	 * This method will create a user without credentials
	 * 
	 * @param createUserRequest
	 * @return
	 */

	public CreateUserResponse createUserWithoutCredentials(CreateUserRequest createUserRequest) {
		log.debug("CreateUserService :: createUserWithoutCredentions " + webClient);
		try {
			return webClient.post().uri(createUserUrl).body(Mono.just(createUserRequest), CreateUserRequest.class)
					.retrieve().bodyToMono(CreateUserResponse.class).block();
		} catch (WebClientResponseException ex) {
			log.debug("Error Response code is : {} and the message is {}", ex.getRawStatusCode(),
					ex.getResponseBodyAsString());
			throw ex;
		} catch (Exception ex) {
			log.debug("Exception in mtd ", ex);
			throw ex;
		}
	}
}