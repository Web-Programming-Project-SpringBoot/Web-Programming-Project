package com.example.taskmanagement.service;

import java.util.List;
import java.util.Optional;

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
	
	public User findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAllWithRoles();
	}
	
	public User getUserById(long id) {
        return userRepository.findByIdWithRoles(id)
                .orElseThrow(() -> new RuntimeException("Kullanici bulunamadi id: " + id));
    }
	
	public User updateUser(long userId, String userName, String email, String password, boolean enable) {
		User existingUser = userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
		existingUser.setUsername(userName);
        existingUser.setEmail(email);
        existingUser.setPassword(password);
        existingUser.setEnabled(enable);
        return userRepository.save(existingUser);
	}
	
	public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

}
