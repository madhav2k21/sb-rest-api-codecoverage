package com.techleads.app.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.techleads.app.model.Users;
import com.techleads.app.repository.UserRepository;

@DataJpaTest //by default it uses in memory database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//to disable in memory database
public class UserRepositoryITest {

	@Autowired
	private UserRepository respository;
	private Users user;
	@BeforeEach
	public void setup() {
		 user = Users.builder().name("madhav").location("Hyderabad").build();
	}

	@DisplayName("testSaveUser")
	@Test
	public void givenUser_whenSave_thenReturnSavedUser() {
		// given -precondition or setup
//		User object

		// when -behavior that we are going to test / action
		Users saved = respository.save(user);
		// then -verify the output
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(0);
	}

	@DisplayName("testFindAllUsers")
	@Test
	public void givenUserList_whenFindAll_thenUsers() {
		// given -precondtion or setup
		Users u2 = Users.builder().name("dill").location("usa").build();
		Users u3 = Users.builder().name("teja").location("RJ").build();
		respository.save(user);
		respository.save(u2);
		respository.save(u3);
		// when -action or behavior that we are going to test
		List<Users> users = respository.findAll();

		// then -verify the result
		assertThat(users).isNotNull();
		assertThat(users.size()).isEqualTo(3);

	}

	@DisplayName("findUserById")
	@Test
	public void givenUser_whenFindById_thenUser() {
		// given -precondtion or setup
		respository.save(user);

		// when -action or behavior that we are going to test

		Optional<Users> findById = respository.findById(user.getId());
		// then -verify the result

		assertThat(findById.get()).isNotNull();
		assertThat(findById.get().getId()).isGreaterThan(0);
		assertThat(findById.get().getName()).isEqualTo("madhav");
		assertThat(findById.get().getLocation()).isEqualTo("Hyderabad");
	}

	@DisplayName("findByName")
	@Test
	public void givenUser_whenName_thenUser() {
		// given -precondition or setup
		respository.save(user);
		String name = "madhav";
		// when - behavior that we are going to test or action
		Optional<Users> findByName = respository.findByName(name);
		// then - verify the result
//		assertThat(findByName.get()).isNotNull();
		assertThat(findByName.get().getName()).isEqualTo(name);

	}

	@DisplayName("updateUserById")
	@Test
	public void givenUser_whenUpdateUser_thenUpdatedUser() {
		// given -precondition or setup
		Users save = respository.save(user);
		// when - behavior that we are going to test or action
		Optional<Users> findByName = respository.findById(save.getId());
		findByName.get().setName("Suresh");
		findByName.get().setLocation("Hyderabad");
		respository.save(findByName.get());
		// then - verify the result
		assertThat(findByName.get()).isNotNull();
		assertThat(findByName.get().getName()).isEqualTo("Suresh");
		assertThat(findByName.get().getLocation()).isEqualTo("Hyderabad");

	}

	@DisplayName("deleteUserById")
	@Test
	public void givenUser_whenDelete_thenRemoveUser() {

		// given -precondition or setup
		Users save = respository.save(user);
		// when - behavior that we are going to test or action
		respository.delete(save);
		Optional<Users> findById = respository.findById(save.getId());
		// then - verify the result
		assertThat(findById.isPresent()).isFalse();
		assertThat(findById).isEmpty();
	}

	@DisplayName("findByJPQL")
	@Test
	public void givenNameLocation_whenFindByJPQL_thenUser() {
		// given -precondition or setup
		respository.save(user);
		String name="madhav";
		String location="Hyderabad";
		// when - behavior that we are going to test or action
		Users findByJPQL = respository.findByJPQL(name, location);
		// then - verify the result
		
		assertThat(findByJPQL).isNotNull();
		assertThat(findByJPQL.getName()).isEqualTo(name);
		assertThat(findByJPQL.getLocation()).isEqualTo(location);
	}
	
	@DisplayName("findByJPQLNamedParams")
	@Test
	public void givenNameLocation_whenFindByJPQLNamedParam_thenUser() {
		// given -precondition or setup
		respository.save(user);
		String name="madhav";
		String location="Hyderabad";
		// when - behavior that we are going to test or action
		Users findByJPQL = respository.findByJPQLNamedParams(name, location);
		// then - verify the result
		
		assertThat(findByJPQL).isNotNull();
		assertThat(findByJPQL.getName()).isEqualTo(name);
		assertThat(findByJPQL.getLocation()).isEqualTo(location);
	}
	
	
	@DisplayName("findByNativeSQLQuery")
	@Test
	public void givenNameLocation_whenfindByNativeQuery_thenReturnUser() {
		// given -precondition or setup
		respository.save(user);
		String name="madhav";
		String location="Hyderabad";
		// when - behavior that we are going to test or action
		Users findByJPQL = respository.findByNativeQuery(name, location);
		// then - verify the result
		
		assertThat(findByJPQL).isNotNull();
		assertThat(findByJPQL.getName()).isEqualTo(name);
		assertThat(findByJPQL.getLocation()).isEqualTo(location);
	}
	
	@DisplayName("findByNativeSQLQueryNamedParams")
	@Test
	public void givenNameLocation_whenfindByNativeQueryNamedParams_thenReturnUser() {
		// given -precondition or setup
		respository.save(user);
		String name="madhav";
		String location="Hyderabad";
		// when - behavior that we are going to test or action
		Users findByJPQL = respository.findByNativeQueryNamedParam(name, location);
		// then - verify the result
		
		assertThat(findByJPQL).isNotNull();
		assertThat(findByJPQL.getName()).isEqualTo(name);
		assertThat(findByJPQL.getLocation()).isEqualTo(location);
	}

}
