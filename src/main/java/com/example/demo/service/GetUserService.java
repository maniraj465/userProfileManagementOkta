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
 *  * This service will get user with login  *  * @author msivasubbu001  *  
 */

@Component
@Service
@Slf4j
public class GetUserService {
	@Qualifier("oktaWebClient")
	@Autowired
	WebClient webClient;

	@Value("${get.user.url}")
	public String getUserUrl;

	/**
	 * This method will get user with login
	 * 
	 * @param login
	 * @return
	 */

	public GetUserResponse getUserWithLogin(String login) {
		log.debug("GetUserService :: getUserwithLogin " + webClient);
		String url = getUserUrl + login;
		try {
			return webClient.get().uri(url).retrieve().bodyToMono(GetUserResponse.class).block();
		} catch (WebClientResponseException ex) {
			log.debug("Error Response code is : {} and the message is {}", ex.getRawStatusCode(),
					ex.getResponseBodyAsString());
			throw ex;
		} catch (Exception ex) {
			log.debug("Exception in mtd " + ex);
			throw ex;
		}
	}

}
