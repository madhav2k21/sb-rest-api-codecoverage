package com.techleads.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techleads.app.exception.ResourseNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<Users> findAllUsers() {
		List<Users> users = userRepository.findAll();
		return users;
	}

	public Optional<Users> findUserById(Integer id) {
		return userRepository.findById(id);
	}

	public Users saveUser(Users user) {
		Optional<Users> existingUser = userRepository.findByName(user.getName());
		if(existingUser.isPresent()) {
			throw new ResourseNotFoundException("User is already present with name: "+user.getName());
		}
		
		Users savedUser = userRepository.save(user);
		return savedUser;

	}

	public String updateUserById(Users user, Integer id) {
		user.setId(id);

		if (userRepository.findById(id).isPresent()) {

			userRepository.save(user);
			return "User is Updated successfully with Id: " + id;
		}

		return "Failed to Update the user details";
	}

	public String deleteUserById(Integer id) {
		if (userRepository.findById(id).isPresent()) {

			userRepository.deleteById(id);
			return "User is deleted  successfully with Id: " + id;
		}

		return "Failed to Delete the user";
	}

}
