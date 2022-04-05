package com.techleads.app.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techleads.app.model.Users;
import com.techleads.app.service.UserService;

@WebMvcTest
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private Users user;

	@BeforeEach
	public void setup() {
		user = Users.builder().id(1001).name("dill").location("USA").build();
	}

	@DisplayName("testSaveUser")
	@Test
	public void givenUser_whenSaveUser_thenReturnUser() throws JsonProcessingException, Exception {
		// given -pre condtion or setup
		given(userService.saveUser(any(Users.class)))
				.willAnswer((invocation) -> invocation.getArgument(0));
		// when -behavior that we are going to test or action
		ResultActions response = mockMvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

		// then -verify the result
		response
		.andDo(print()).andExpect(status().isCreated())
		
		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(jsonPath("$.location", is(user.getLocation())))
		.andExpect(jsonPath("$.id", is(user.getId())));

	}

	@DisplayName("testFindAllUsers")
	@Test
	public void givenUsersList_whenFindAll_thenReturnUsers() throws Exception {
		//given -precondition or setup
		Users u1 = Users.builder().id(1002).name("Madhav").location("Hyderabad").build();
		List<Users> users = List.of(user, u1);
		given(userService.findAllUsers()).willReturn(users);
		//when -behavior that we are testing or action
		ResultActions response = mockMvc.perform(get("/users"));
		//then -verify the output
		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(users.size())))
		;
	}
	
	@DisplayName("testFindUserById")
	@Test
	public void givenUserId_whenFindById_thenReturnUser() throws Exception {
		//given -precondition or setup
		Integer id=1001;
		given(userService.findUserById(id)).willReturn(Optional.of(user));
		//when -behavior that we are going to test or action
		ResultActions response = mockMvc.perform(get("/users/"+id));
		//then -verify the output
		response.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(jsonPath("$.location", is(user.getLocation())))
				
		;
		
	}
	
	@DisplayName("testFindUserByIdNegative")
	@Test
	public void givenUserId_whenFindById_thenReturnNotExist() throws Exception {
		//given -precondition or setup
		Integer id=1002;
		given(userService.findUserById(id)).willReturn(Optional.empty());
		//when -behavior that we are going to test or action
		ResultActions response = mockMvc.perform(get("/users/"+id));
		//then -verify the output
		response.andExpect(status().isNotFound())
		.andDo(print())
		;
		
	}
	

}
