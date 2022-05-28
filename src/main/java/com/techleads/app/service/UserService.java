package com.techleads.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.techleads.app.exception.ResourseNotFoundException;
import com.techleads.app.model.Users;
import com.techleads.app.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	@Value("${user.createddte}")
	private String createdDate;
	@Value("${user.updateddte}")
	private String updatedDate;

//	@PostConstruct
//	public void initDB() {
//		List<Users> products = IntStream.rangeClosed(1, 200).mapToObj(i -> new Users(randomName(), randomName()))
//				.collect(Collectors.toList());
//		userRepository.saveAll(products);
//	}

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<Users> findAllUsers() {
		List<Users> users = userRepository.findAll();
		
		return users;
	}

	public List<Users> findProductsWithSorting(String field) {
//		return userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
//		return userRepository.findAll(Sort.by("name").descending().and(Sort.by("location").ascending()));
		return userRepository.findAll(Sort.by("createdDateTime").ascending().and(Sort.by("updatedDateTime").ascending()));
//		return userRepository.findAll(Sort.by(createdDate).descending().and(Sort.by(updatedDate).descending()));
//		return userRepository.findAll(Sort.by("createdDateTime").ascending().and(Sort.by("updatedDateTime").ascending()));
		
	}
	
	
	public List<Users> findAllByOrderByCreatedDateTimeAndUpdatedDateTime() {
		return userRepository.findAllByOrderByCreatedDateTimeAscUpdatedDateTimeAsc();
		
	}

	public Optional<Users> findUserById(Integer id) {
		return userRepository.findById(id);
	}

	public Users saveUser(Users user) {
		Optional<Users> existingUser = userRepository.findByName(user.getName());
		if (existingUser.isPresent()) {
			throw new ResourseNotFoundException("User is already present with name: " + user.getName());
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

	public static String randomName() {
		// create a string of all characters
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		// create random string builder
		StringBuilder sb = new StringBuilder();

		// create an object of Random class
		Random random = new Random();

		// specify length of random string
		int length = 7;

		for (int i = 0; i < length; i++) {

			// generate random index number
			int index = random.nextInt(alphabet.length());

			// get character specified by index
			// from the string
			char randomChar = alphabet.charAt(index);

			// append the character to string builder
			sb.append(randomChar);
		}

		String firstLetStr = sb.substring(0, 1);
		// Get remaining letter using substring
		String remLetStr = sb.substring(1);
		firstLetStr = firstLetStr.toUpperCase();

		return (firstLetStr + remLetStr);
	}

}
