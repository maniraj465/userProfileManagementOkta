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
 * This service will get all the users in the group
 * 
 * @author msivasubbu001
 *
 */

@Component
@Service
@Slf4j
public class GetGroupUsersService {
	@Qualifier("oktaWebClient")
	@Autowired
	WebClient webClient;

	@Value("${get.group.users.url.postfix}")
	public String getGroupUsersUrlPostfix;
	@Value("${get.group.users.url.prefix}")
	public String getGroupUsersUrlPrefix;

	/**
	 * This method will get all the user in the group
	 * 
	 * @param groupId
	 * @return
	 */

	public GetUserResponse[] getGroupUsers(String groupId) {
		log.debug("GetGroupUsersService :: getGroupUsers " + webClient);
		String url = getGroupUsersUrlPrefix + groupId + getGroupUsersUrlPostfix;
		try {
			return webClient.get().uri(url).retrieve().bodyToMono(GetUserResponse[].class).block();
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