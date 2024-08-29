package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.model.GetUserResponse;
import com.example.demo.service.GetCurrentUserService;
import com.example.demo.service.GetGroupUsersService;
import com.example.demo.service.GetUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class will check the permission of current user
 * 
 * @author msivasubbu001
 *
 */

@Slf4j
@Component
public class CheckPermission {
	
	@Value("${group.id}")
	public String groupId;
	@Qualifier("oktaWebClient")
	@Autowired
	WebClient webClient;
	@Autowired
	GetUserService getUserService;	
	@Autowired
	GetCurrentUserService getCurrentUserService;
	@Autowired
	GetGroupUsersService getGroupUsersService;
	
	/**
	 * This method will check the permission of current user
	 * 
	 * @param userLogin
	 * @return
	 */
	
	public boolean checkPermission(String login) {
		System.out.println("GetGroupUsersService :: getGroupUsers " + webClient);
		boolean userEmailFlage = false, currentUserEmailFlag = false;
		GetUserResponse getUserResponse = getUserService.getUserWithLogin(login);
		GetUserResponse getCurrentUserResponse = getCurrentUserService.getCurrentUser();
		String userEmail = getUserResponse.getProfile().getEmail();
		String currentUserEmail = getCurrentUserResponse.getProfile().getEmail();
		GetUserResponse[] getGroupUsersResponse = getGroupUsersService.getGroupUsers(groupId);
		
		for(GetUserResponse user : getGroupUsersResponse) {
			if(userEmail.equals(user.getProfile().getEmail())) {
				userEmailFlage = true;
			}
			if(currentUserEmail.equals(user.getProfile().getEmail())) {
				currentUserEmailFlag = true;
			}
			if(userEmailFlage && currentUserEmailFlag) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkPermission(GetUserResponse getUserResponse) {
		System.out.println("GetGroupUsersService :: getGroupUsers " + webClient);
		boolean userEmailFlage = false, currentUserEmailFlag = false;
		GetUserResponse getCurrentUserResponse = getCurrentUserService.getCurrentUser();
		String userEmail = getUserResponse.getProfile().getEmail();
		String currentUserEmail = getCurrentUserResponse.getProfile().getEmail();
		GetUserResponse[] getGroupUsersResponse = getGroupUsersService.getGroupUsers(groupId);
		
		for(GetUserResponse user : getGroupUsersResponse) {
			if(userEmail.equals(user.getProfile().getEmail())) {
				userEmailFlage = true;
			}
			if(currentUserEmail.equals(user.getProfile().getEmail())) {
				currentUserEmailFlag = true;
			}
			if(userEmailFlage && currentUserEmailFlag) {
				return true;
			}
		}
		return false;
	}
}