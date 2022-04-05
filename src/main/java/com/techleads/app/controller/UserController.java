package com.techleads.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techleads.app.model.Users;
import com.techleads.app.service.UserService;

@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	// http://localhost:8082/users/101
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<Users> findUserById(@PathVariable("id") Integer id) {
		logger.info("Inside findUserById in UserController");
		return userService.findUserById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	// http://localhost:8082/users
	@GetMapping(value = "/users")
	public List<Users> findAllUsers() {
		logger.info("Inside findAllUsers in UserController");
		List<Users> users = userService.findAllUsers();
		logger.info("Exiting from findAllUsers in UserController");
		return users;
	}

	@PostMapping(value = "/users")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Users saveUser(@RequestBody Users user) {
		logger.info("Inside saveUser in UserController");
		user = userService.saveUser(user);
		logger.info("Exiting from saveUser in UserController");
		return user;
	}
	// ****************Sample request body to save User resource End

	// http://localhost:8082/users/101
	@DeleteMapping(value = "/users/{id}")
	public String deleteUserById(@PathVariable("id") Integer id) {
		logger.info("Inside deleteUserById in UserController");
		String result = userService.deleteUserById(id);
		logger.info("Exiting from deleteUserById in UserController");
		return result;
	}

	@PutMapping(value = "/users/{id}")
	public String updateUserById(@RequestBody Users user, @PathVariable("id") Integer id) {
		logger.info("Inside updateUserById in UserController");
		String result = userService.updateUserById(user, id);
		logger.info("Exiting from updateUserById in UserController");
		return result;
	}

	// http://localhost:8082/users1/101
	@GetMapping(value = "/v1/users/{id}")
	public ResponseEntity<Users> findUserById1(@PathVariable("id") Integer id) {
		logger.info("Inside findUserById1 in UserController");
		Users user = userService.findUserById(id).get();
		logger.info("Exiting from findUserById1 in UserController");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	// http://localhost:8082/users1
	@GetMapping(value = "/v1/users")
	public ResponseEntity<List<Users>> findAllUsers1() {
		logger.info("Inside findAllUsers1 in UserController");
		List<Users> users = userService.findAllUsers();
		logger.info("Exiting from findAllUsers1 in UserController");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PostMapping(value = "/v1/users")
	public ResponseEntity<Users> saveUser1(@RequestBody Users user) {
		logger.info("Inside saveUser1 in UserController");
		user = userService.saveUser(user);
		logger.info("Exiting from saveUser1 in UserController");
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PutMapping(value = "/v1/users/{id}")
	public ResponseEntity<String> updateUserById1(@RequestBody Users user, @PathVariable("id") Integer id) {
		logger.info("Inside updateUserById in UserController");
		String result = userService.updateUserById(user, id);
		logger.info("Exiting from updateUserById in UserController");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
