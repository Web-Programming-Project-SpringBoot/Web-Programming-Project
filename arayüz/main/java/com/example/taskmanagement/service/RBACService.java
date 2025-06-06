package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.Permission;
import com.example.taskmanagement.entity.PermissionName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RBACService {
	@Autowired
    private PermissionService permissionService;
    
    public boolean hasPermission(Long userId, PermissionName permissionName) {
        List<Permission> permissions = permissionService.findByUserId(userId);
        return permissions.stream()
                .anyMatch(permission -> permission.getName().equals(permissionName));
    }
    
    public boolean hasPermission(Long userId, String resource, String action) {
        List<Permission> permissions = permissionService.findByUserId(userId);
        return permissions.stream()
                .anyMatch(permission -> 
                    permission.getResource().equals(resource) && 
                    permission.getAction().equals(action));
    }
    
    public List<PermissionName> getUserPermissions(Long userId) {
        List<Permission> permissions = permissionService.findByUserId(userId);
        return permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
    }
    
    public List<String> getUserPermissionsForResource(Long userId, String resource) {
        List<Permission> permissions = permissionService.findByUserId(userId);
        return permissions.stream()
                .filter(permission -> permission.getResource().equals(resource))
                .map(Permission::getAction)
                .collect(Collectors.toList());
    }
}