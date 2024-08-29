package com.example.demo.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CreateUserRequest;
import com.example.demo.model.CreateUserResponse;
import com.example.demo.service.CreateUserService;

/**
 * This controller will create a user
 * 
 * @author msivasubbu001
 *
 */

@RestController
@RequestMapping("createuser")
public class CreateUserController {

	@Autowired
	CreateUserService createUserService;

	/**
	 * This API will create a user
	 * 
	 * @param createUserRequest
	 * @return
	 * @throws Exception
	 */

	@PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
	public CreateUserResponse createUserWithoutCredentials(
			@Valid @RequestBody CreateUserRequest createUserRequest) throws Exception {
		return createUserService.createUserWithoutCredentials(createUserRequest);
	}

}