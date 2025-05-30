package com.example.taskmanagement.controller;


import com.example.taskmanagement.service.RBACService;
import com.example.taskmanagement.service.UserService;
import com.example.taskmanagement.entity.PermissionName;
import com.example.taskmanagement.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rbac")
public class RBACTestController {
	@Autowired
    private RBACService rbacService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/user/{userId}/permissions")
    public ResponseEntity<Map<String, Object>> getUserPermissions(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
            
            List<PermissionName> permissions = rbacService.getUserPermissions(userId);
            
            response.put("success", true);
            response.put("user", user.getUsername());
            response.put("permissions", permissions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/user/{userId}/check-permission/{permissionName}")
    public ResponseEntity<Map<String, Object>> checkPermission(
            @PathVariable Long userId,
            @PathVariable String permissionName) {
        Map<String, Object> response = new HashMap<>();
        try {
            PermissionName permission = PermissionName.valueOf(permissionName);
            boolean hasPermission = rbacService.hasPermission(userId, permission);
            
            response.put("success", true);
            response.put("hasPermission", hasPermission);
            response.put("permission", permissionName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/user/{userId}/check-resource-permission")
    public ResponseEntity<Map<String, Object>> checkResourcePermission(
            @PathVariable Long userId,
            @RequestParam String resource,
            @RequestParam String action) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean hasPermission = rbacService.hasPermission(userId, resource, action);
            
            response.put("success", true);
            response.put("hasPermission", hasPermission);
            response.put("resource", resource);
            response.put("action", action);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/user/{userId}/resource-permissions/{resource}")
    public ResponseEntity<Map<String, Object>> getResourcePermissions(
            @PathVariable Long userId,
            @PathVariable String resource) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> permissions = rbacService.getUserPermissionsForResource(userId, resource);
            
            response.put("success", true);
            response.put("resource", resource);
            response.put("permissions", permissions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<User> users = userService.findAll();
            response.put("success", true);
            response.put("users", users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
