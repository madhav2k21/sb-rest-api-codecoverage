package com.techleads.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techleads.app.exception.ResourseNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserService userService;

	private Users user;

	@BeforeEach
	public void setup() {
//		userRepository=Mockito.mock(UserRepository.class);
//		userService= new UserService(userRepository);
		user = Users.builder().id(1001).name("madhav").location("Hyderabad").build();
	}

	@DisplayName("saveEmployee")
	@Test
	public void givenUser_whenSaveUser_thenReturnUserObject() {
		// given - precondtion or setup

		given(userRepository.findByName(user.getName())).willReturn(Optional.empty());
		given(userRepository.save(user)).willReturn(user);
		// when -behavior that we are going to test or action
		Users saveUser = userService.saveUser(user);

		// then - verify the result
		assertThat(saveUser).isNotNull();
		assertThat(saveUser.getId()).isEqualTo(1001);
		System.out.println(userRepository);
		System.out.println(userService);
		System.out.println(saveUser);
		verify(userRepository, times(1)).findByName(user.getName());
		verify(userRepository, times(1)).save(user);
	}

	@DisplayName("saveUserWithException")
	@Test
	public void givenUser_whenSaveUser_thenThrowsCustomException() {
		// given - precondtion or setup

		given(userRepository.findByName(user.getName())).willReturn(Optional.of(user));
//		given(userRepository.save(user)).willReturn(user);
		// when -behavior that we are going to test or action

		assertThrows(ResourseNotFoundException.class, () -> {
			userService.saveUser(user);
		});
		// then - verify the result
		verify(userRepository, never()).save(user);
	}

	@DisplayName("findAllUsers")
	@Test
	public void givenUsers_whenFindAll_thenReturnUsers() {

		// given
		Users u1 = Users.builder().id(1002).name("dill").location("USA").build();
		given(userRepository.findAll()).willReturn(List.of(user, u1));
		// when
		List<Users> users = userService.findAllUsers();
		// verify
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(2);
	}

	@DisplayName("findAllUsersReturnsEmptyList")
	@Test
	public void givenEmptyUsers_whenFindAll_thenReturnEmptyList() {

		// given
		Users u1 = Users.builder().id(1002).name("dill").location("USA").build();
		given(userRepository.findAll()).willReturn(Collections.emptyList());
		// when
		List<Users> users = userService.findAllUsers();
		// verify
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(0);
	}

	@DisplayName("findUserById")
	@Test
	public void givenId_whenFindById_thenReturnUser() {

		// given

		given(userRepository.findById(1001)).willReturn(Optional.of(user));
		// when
		Users user = userService.findUserById(1001).get();
		// verify
		assertThat(user).isNotNull();
		assertThat(user.getId()).isEqualTo(1001);
		assertThat(user.getName()).isEqualTo("madhav");
		assertThat(user.getLocation()).isEqualTo("Hyderabad");
	}

	@DisplayName("DeleteUserById")
	@Test
	public void givenId_whenDeleteUserById_thenReturnResult() {
		Integer id = 1001;

		// given
		given(userRepository.findById(user.getId())).willReturn(Optional.of(user));
		willDoNothing().given(userRepository).deleteById(id);

		// when
		String updateUserById = userService.deleteUserById(id);
		// verify
		assertThat(user).isNotNull();
		assertThat(updateUserById).isEqualTo("User is deleted  successfully with Id: " + id);
		verify(userRepository, times(1)).deleteById(id);

	}

	@DisplayName("DeleteUserByIdNegativeCase")
	@Test
	public void givenId_whenDeleteUserById_thenFailureMessage() {
		Integer id = 1001;

		// given
		given(userRepository.findById(user.getId())).willReturn(Optional.empty());
//		willDoNothing().given(userRepository).deleteById(id);;

		// when
		String updateUserById = userService.deleteUserById(id);
		// verify
		assertThat(updateUserById).isEqualTo("Failed to Delete the user");
		verify(userRepository, never()).deleteById(id);

	}

}
