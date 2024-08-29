package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.DeleteUserService;
import com.example.demo.util.CheckPermission;

/**
 *  This controller will delete a user with ID
 * 
 * @author msivasubbu001
 *
 */

@RestController
@RequestMapping("deleteusers")
public class DeleteUserController {

	@Autowired
	DeleteUserService deleteUserService;
	@Autowired
	CheckPermission checkPermission;
	
	/**
	 *  This API will delete a user with ID
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	
	@DeleteMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) throws Exception{
		if (checkPermission.checkPermission(id)) {
			return deleteUserService.deleteUser(id);
		} else {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
	}
}