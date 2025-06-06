package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.Permission;
import com.example.taskmanagement.entity.PermissionName;
import com.example.taskmanagement.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class PermissionService {
	@Autowired
    private PermissionRepository permissionRepository;
    
    public Permission createPermission(PermissionName name, String displayName, String description, String resource, String action) {
        if (permissionRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Bu yetki zaten var: " + name);
        }
        
        Permission permission = new Permission(name, displayName, description, resource, action);
        return permissionRepository.save(permission);
    }
    
    public Optional<Permission> findByName(PermissionName name) {
        return permissionRepository.findByName(name);
    }
    
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }
    
    public List<Permission> findByUserId(Long userId) {
        return permissionRepository.findByUserId(userId);
    }
    
    public List<Permission> findByRoleName(String roleName) {
        return permissionRepository.findByRoleName(roleName);
    }
}
