package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.demo.model.GetUserResponse;

import lombok.extern.slf4j.Slf4j;

/**
 *  * This service will get current user  *  * @author msivasubbu001  *  
 */

@Component
@Service
@Slf4j
public class GetCurrentUserService {
	@Qualifier("oktaWebClient")
	@Autowired
	WebClient webClient;

	@Value("${get.current.user.url}")
	public String getCurrentUserUrl;

	/**
	 * This method will get current user
	 * 
	 * @return
	 */
	
	public GetUserResponse getCurrentUser() {
		log.debug("GetCurrentUserService :: getCurrentUser " + webClient);
		try {
			return webClient.get().uri(getCurrentUserUrl).retrieve().bodyToMono(GetUserResponse.class).block();
		} catch (WebClientResponseException ex) {
			log.debug("Error Response code is : {} and the message is {}", ex.getRawStatusCode(), ex.getResponseBodyAsString());
			throw ex;
		} catch (Exception ex) {
			log.debug("Exception in mtd ", ex);
			throw ex;
		}
	}
}