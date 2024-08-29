package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.UpdateUserRequest;
import com.example.demo.model.UpdateUserResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * This service will update user with user ID
 * 
 * @author msivasubbu001
 *
 */

@Component
@Service
@Slf4j
public class UpdateUserService {
	@Qualifier("oktaWebClient")
	@Autowired
	WebClient webClient;

	@Value("${update.user.url}")
	public String updateUserUrl;

	/**
	 * This method will update user with user ID
	 * 
	 * @param updateUserRequest
	 * @param id
	 * @return
	 */

	public UpdateUserResponse UpdateProfilewithID(UpdateUserRequest updateUserRequest, String id) {
		log.debug("UpdateUserService :: UpdateProfilewithID " + webClient);

		return webClient.post().uri(updateUserUrl + id).body(Mono.just(updateUserRequest), UpdateUserRequest.class)
				.retrieve()
				.bodyToMono(UpdateUserResponse.class).block();
	}
}
