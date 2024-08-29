package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.extern.slf4j.Slf4j;

/**
 * This service will delete a user with user ID
 * 
 * @author msivasubbu001
 *
 */

@Component
@Service
@Slf4j
public class DeleteUserService {
	@Qualifier("oktaWebClient")
	@Autowired
	WebClient webClient;

	@Value("${delete.user.url}")
	public String deleteUserUrl;

	/**
	 * This method will delete a user with user ID
	 * 
	 * @param id
	 * @return 
	 * @return
	 */

	public ResponseEntity<Void> deleteUser(String id) {
		log.debug("GetUserService :: getUserwithLogin " + webClient);
		String url = deleteUserUrl + id;
		try {
			return webClient.delete().uri(url).retrieve().toBodilessEntity().block();
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