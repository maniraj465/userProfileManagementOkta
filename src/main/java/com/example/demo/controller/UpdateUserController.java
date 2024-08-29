package com.example.demo.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.GetUserResponse;
import com.example.demo.model.UpdateUserRequest;
import com.example.demo.model.UpdateUserResponse;
import com.example.demo.service.GetUserService;
import com.example.demo.service.UpdateUserService;
import com.example.demo.util.CheckPermission;

/**
 * This controller will update a user profile with ID
 * 
 * @author msivasubbu001
 *
 */

@RestController
@RequestMapping("updateuser")
public class UpdateUserController {

	@Autowired
	UpdateUserService updateUserService;
	@Autowired
	CheckPermission checkPermission;
	@Autowired
	GetUserResponse getUserResponse;
	@Autowired
	UpdateUserResponse updateUserResponse;
	@Autowired
	GetUserService getUserService;

	/**
	 * This API will update a user profile with login
	 * 
	 * @param updateUserRequest
	 * @return
	 * @throws Exception
	 */

	@PostMapping(path = "update/{login}", consumes = "application/json", produces = "application/json")
	public UpdateUserResponse UpdateProfilewithID(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable @NotNull String login)
			throws Exception {
		getUserResponse = getUserService.getUserWithLogin(login);
		if (getUserResponse.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User dosen't exist.");
		}
		if (checkPermission.checkPermission(getUserResponse)) {
			updateUserResponse = updateUserService.UpdateProfilewithID(updateUserRequest, getUserResponse.getId());
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"You don't have permission to update the user.");
		}
		return updateUserResponse;
	}
}