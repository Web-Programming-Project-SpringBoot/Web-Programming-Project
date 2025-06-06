package com.example.taskmanagement.controller;


import com.example.taskmanagement.service.RBACService;
import com.example.taskmanagement.service.UserService;
import com.example.taskmanagement.service.RoleService;
import com.example.taskmanagement.service.PermissionService;
import com.example.taskmanagement.entity.PermissionName;
import com.example.taskmanagement.entity.RoleName;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rbac")
public class RBACTestController {
	@Autowired
    private RBACService rbacService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private PermissionService permissionService;
    
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
    
    @GetMapping("/roles")
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Role> roles = roleService.findAll();
            response.put("success", true);
            response.put("roles", roles);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/user/{userId}/assign-role")
    public ResponseEntity<Map<String, Object>> assignRoleToUser(
            @PathVariable Long userId,
            @RequestBody RoleAssignmentRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.assignRoleToUser(userId, request.getRoleName());
            
            response.put("success", true);
            response.put("message", "Rol başarıyla atandı");
            response.put("user", user.getUsername());
            response.put("assignedRole", request.getRoleName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/user/{userId}/assign-multiple-roles")
    public ResponseEntity<Map<String, Object>> assignMultipleRolesToUser(
            @PathVariable Long userId,
            @RequestBody MultipleRoleAssignmentRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.assignMultipleRolesToUser(userId, request.getRoleNames());
            
            response.put("success", true);
            response.put("message", "Roller başarıyla atandı");
            response.put("user", user.getUsername());
            response.put("assignedRoles", request.getRoleNames());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/role/{roleName}/assign-permission")
    public ResponseEntity<Map<String, Object>> assignPermissionToRole(
            @PathVariable String roleName,
            @RequestBody PermissionAssignmentRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            RoleName role = RoleName.valueOf(roleName);
            Role updatedRole = roleService.assignPermission(role, request.getPermissionNames());
            
            response.put("success", true);
            response.put("message", "Yetkiler role başarıyla atandı");
            response.put("role", roleName);
            response.put("assignedPermissions", request.getPermissionNames());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // ============ YETKİ KALDIRMA İŞLEMLERİ (DELETE) ============
    
    @DeleteMapping("/user/{userId}/remove-role/{roleName}")
    public ResponseEntity<Map<String, Object>> removeRoleFromUser(
            @PathVariable Long userId,
            @PathVariable String roleName) {
        Map<String, Object> response = new HashMap<>();
        try {
            RoleName role = RoleName.valueOf(roleName);
            User user = userService.removeRoleFromUser(userId, role);
            
            response.put("success", true);
            response.put("message", "Rol başarıyla kaldırıldı");
            response.put("user", user.getUsername());
            response.put("removedRole", roleName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @DeleteMapping("/role/{roleName}/remove-permission/{permissionName}")
    public ResponseEntity<Map<String, Object>> removePermissionFromRole(
            @PathVariable String roleName,
            @PathVariable String permissionName) {
        Map<String, Object> response = new HashMap<>();
        try {
            RoleName role = RoleName.valueOf(roleName);
            PermissionName permission = PermissionName.valueOf(permissionName);
            Role updatedRole = roleService.removePermission(role, permission);
            
            response.put("success", true);
            response.put("message", "Yetki rolden başarıyla kaldırıldı");
            response.put("role", roleName);
            response.put("removedPermission", permissionName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
 // Roller ve yetkiler için CRUD işlemleri
    @PostMapping("/roles")
    public ResponseEntity<Map<String, Object>> createRole(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            String roleName = requestBody.get("name");
            String displayName = requestBody.get("displayName");
            String description = requestBody.get("description");
            
            RoleName role = RoleName.valueOf(roleName);
            Role createdRole = roleService.createRole(role, displayName, description);
            
            response.put("success", true);
            response.put("message", "Rol başarıyla oluşturuldu");
            response.put("role", createdRole);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/permissions")
    public ResponseEntity<Map<String, Object>> createPermission(@RequestBody Map<String, String> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            String permissionName = requestBody.get("name");
            String displayName = requestBody.get("displayName");
            String description = requestBody.get("description");
            String resource = requestBody.get("resource");
            String action = requestBody.get("action");
            
            PermissionName permission = PermissionName.valueOf(permissionName);
            Permission createdPermission = permissionService.createPermission(
                permission, displayName, description, resource, action);
            
            response.put("success", true);
            response.put("message", "Yetki başarıyla oluşturuldu");
            response.put("permission", createdPermission);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // ============ DTO SINIFLAR ============
    
    public static class RoleAssignmentRequest {
        private RoleName roleName;
        
        public RoleName getRoleName() { return roleName; }
        public void setRoleName(RoleName roleName) { this.roleName = roleName; }
    }
    
    public static class MultipleRoleAssignmentRequest {
        private Set<RoleName> roleNames;
        
        public Set<RoleName> getRoleNames() { return roleNames; }
        public void setRoleNames(Set<RoleName> roleNames) { this.roleNames = roleNames; }
    }
    
    public static class PermissionAssignmentRequest {
        private Set<PermissionName> permissionNames;
        
        public Set<PermissionName> getPermissionNames() { return permissionNames; }
        public void setPermissionNames(Set<PermissionName> permissionNames) { this.permissionNames = permissionNames; }
    }
}
