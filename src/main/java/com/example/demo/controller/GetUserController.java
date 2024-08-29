package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.GetUserResponse;
import com.example.demo.service.GetUserService;
import com.example.demo.util.EncryptDecryptUtil;

/**
 *  This controller will get a user with login
 * 
 * @author msivasubbu001
 *
 */

@RestController
@RequestMapping("getusers")
public class GetUserController {

	@Autowired
	GetUserService getUserService;
	
	@Value("${okta.api.token}")
	public String oktaApiToken;
	@Autowired
	EncryptDecryptUtil encryptDecryptUtil;
	
	/**
	 *  This API will get a user with login
	 * 
	 * @param login
	 * @return
	 * @throws Exception
	 */
	
	@GetMapping(path = "/{login}", produces = "application/json")
	public GetUserResponse getUserwithLogin(@PathVariable("login") String login) throws Exception{
		
		return getUserService.getUserWithLogin(login);
	}

}