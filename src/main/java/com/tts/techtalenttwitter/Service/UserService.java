package com.tts.techtalenttwitter.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tts.techtalenttwitter.Model.Role;
import com.tts.techtalenttwitter.Model.User;
import com.tts.techtalenttwitter.Repository.RoleRepository;
import com.tts.techtalenttwitter.Repository.UserRepository;

//What the @Service annotation means is that we can
// ask SpringBoot to create a UserService
//and it is marked with @Service, Spring Boot will
//know that it can just go ahead and create one with
// using the UserService constructor to make one

@Service
public class UserService {

	// this is called injection
	// automatically create the class and fill it in

	@Autowired
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService(UserRepository userRepository,
			RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public User saveNewUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("USER");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		return userRepository.save(user);
	}

	public User getLoggedInUser() {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		return findByUsername(loggedInUsername);

	}
}
