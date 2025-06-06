package com.example.taskmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.entity.RoleName;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.repository.RoleRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	public User findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}
	
	public List<User> findAll(){
		return userRepository.findAllWithRoles();
	}
	
	public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public List<User> getAllUsersSimple() {
        return userRepository.findAll();
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

	@Transactional
	public User assignRoleToUser(Long userId, RoleName roleName) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + userId));
	    
	    Role role = roleRepository.findByName(roleName)
	            .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + roleName));
	    
	    user.getRoles().add(role);
	    return userRepository.save(user);
	}

	@Transactional
	public User assignMultipleRolesToUser(Long userId, Set<RoleName> roleNames) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + userId));
	    
	    for (RoleName roleName : roleNames) {
	        Role role = roleRepository.findByName(roleName)
	                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + roleName));
	        user.getRoles().add(role);
	    }
	    
	    return userRepository.save(user);
	}

	@Transactional
	public User removeRoleFromUser(Long userId, RoleName roleName) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı: " + userId));
	    
	    Role role = roleRepository.findByName(roleName)
	            .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + roleName));
	    
	    user.getRoles().remove(role);
	    return userRepository.save(user);
	}

}
