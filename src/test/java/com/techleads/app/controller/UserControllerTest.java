package com.techleads.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.test.web.servlet.MvcResult;
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
		user = Users.builder().id(1001).name("madhav").location("Hyderabad").build();
	}
	
	@DisplayName("testSaveUser_fix")
	@Test
	public void givenUser_whenSaveUser_thenReturnUser_fix() throws JsonProcessingException, Exception {
		// given -pre condition or setup
		
		// when -behavior that we are going to test
		when(userService.saveUser(any(Users.class))).thenReturn(user);
		
		ResultActions response = mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))
				);
		
		
		// then -verify the result
		response.andDo(print()).andExpect(status().isCreated())

		.andExpect(jsonPath("$.name", is(user.getName())))
		.andExpect(jsonPath("$.location", is(user.getLocation())))
		.andExpect(jsonPath("$.id", is(user.getId())));
		verify(userService).saveUser(any(Users.class));
	}
	
	@Test
	@DisplayName("updateUserById_fixed")
	public void givenIdAndUserObj_whenUpdateUserById_thenReturnResult() throws JsonProcessingException, Exception {
		// given -pre condition or setup
		Integer id = 1001;
//		given(userService.updateUserById(user, id)).willReturn("User is Updated successfully with Id: " + id);
		when(userService.updateUserById(any(Users.class), any(Integer.class))).thenReturn("User is Updated successfully with Id: " + id);

		// when -behavior that we are going to test
		ResultActions response = mockMvc.perform(put("/users/{id}", id).contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(user)));
		response.andDo(print()).andExpect(status().isOk());
		MvcResult andReturn = response.andReturn();
		System.out.println("{}:::"+andReturn.getResponse().getContentAsString());
		verify(userService, times(1)).updateUserById(any(Users.class), any(Integer.class));
	}
	
	@Test
	@DisplayName("updateUserById_fixed_Negative")
	public void givenIdAndUserObj_whenUpdateUserById_thenReturnErrorMessage() throws JsonProcessingException, Exception {
		// given -pre condition or setup
		Integer id = 1001;
//		given(userService.updateUserById(user, id)).willReturn("User is Updated successfully with Id: " + id);
		when(userService.updateUserById(any(Users.class), any(Integer.class))).thenReturn("Failed to Update the user details");

		// when -behavior that we are going to test
		ResultActions response = mockMvc.perform(put("/users/{id}", id).contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(user)));
		response.andDo(print()).andExpect(status().isOk());
		MvcResult andReturn = response.andReturn();
		System.out.println("{}:::"+andReturn.getResponse().getContentAsString());
		verify(userService, times(1)).updateUserById(any(Users.class), any(Integer.class));
	}
	

	@DisplayName("testSaveUser")
	@Test
	public void givenUser_whenSaveUser_thenReturnUser() throws JsonProcessingException, Exception {
		// given -pre condtion or setup
		given(userService.saveUser(any(Users.class))).willAnswer((invocation) -> invocation.getArgument(0));
		// when -behavior that we are going to test or action
		ResultActions response = mockMvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user)));

		// then -verify the result
		response.andDo(print()).andExpect(status().isCreated())

				.andExpect(jsonPath("$.name", is(user.getName())))
				.andExpect(jsonPath("$.location", is(user.getLocation())))
				.andExpect(jsonPath("$.id", is(user.getId())));
		
//		verify(userService, times(1)).saveUser(user);

	}

	@DisplayName("testFindAllUsers")
	@Test
	public void givenUsersList_whenFindAll_thenReturnUsers() throws Exception {
		// given -precondition or setup
		Users u1 = Users.builder().id(1002).name("Madhav").location("Hyderabad").build();
		List<Users> users = List.of(user, u1);
		given(userService.findAllUsers()).willReturn(users);
		// when -behavior that we are testing or action
		ResultActions response = mockMvc.perform(get("/users"));
		// then -verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(users.size())));
	}

	@DisplayName("testFindUserById")
	@Test
	public void givenUserId_whenFindById_thenReturnUser() throws Exception {
		// given -precondition or setup
		Integer id = 1001;
		given(userService.findUserById(id)).willReturn(Optional.of(user));
		// when -behavior that we are going to test or action
		ResultActions response = mockMvc.perform(get("/users/" + id));
		// then -verify the output
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is(user.getName())))
				.andExpect(jsonPath("$.location", is(user.getLocation())))

		;

	}

	@DisplayName("testFindUserByIdNegative")
	@Test
	public void givenUserId_whenFindById_thenReturnNotExist() throws Exception {
		// given -precondition or setup
		Integer id = 1002;
		given(userService.findUserById(id)).willReturn(Optional.empty());
		// when -behavior that we are going to test or action
		ResultActions response = mockMvc.perform(get("/users/" + id));
		// then -verify the output
		response.andExpect(status().isNotFound()).andDo(print());
		verify(userService, times(1)).findUserById(id);
	}

	
	
	
	
	@Test
	@DisplayName("deleteUserById")
	public void givenUserId_whenDeleteById_thenReturnResult() throws Exception {
		// given -pre condition or setup
		Integer id = 1001;
		given(userService.deleteUserById(id)).willReturn("User is deleted  successfully with Id: " + id);
		// when -behavior that we are going to test
		ResultActions perform = mockMvc.perform(delete("/users/{id}", id));
		//then
		MvcResult andReturn = perform.andReturn();
		verify(userService, times(1)).deleteUserById(id);
		System.out.println(andReturn.getResponse().getContentAsString());
		assertThat(andReturn.getResponse().getContentAsString()).isEqualTo("User is deleted  successfully with Id: " + id);
		perform.andExpect(status().isOk())
		.andDo(print());
	

	}
	
	@Test
	@DisplayName("deleteUserById_Negative")
	public void givenUserId_whenDeleteById_thenReturnErrorResult() throws Exception {
		// given -pre condition or setup
		Integer id = 1001;
		given(userService.deleteUserById(id)).willReturn("Failed to Delete the user");
		// when -behavior that we are going to test
		ResultActions perform = mockMvc.perform(delete("/users/{id}", id));
		//then
		MvcResult andReturn = perform.andReturn();
		verify(userService, times(1)).deleteUserById(id);
		System.out.println(andReturn.getResponse().getContentAsString());
		assertThat(andReturn.getResponse().getContentAsString()).isEqualTo("Failed to Delete the user");
		perform.andExpect(status().isOk())
		.andDo(print());

	}

}
