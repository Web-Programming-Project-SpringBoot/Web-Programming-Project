package com.example.taskmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public User findByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

}
