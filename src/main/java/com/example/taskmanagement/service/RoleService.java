package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.Role;
import com.example.taskmanagement.entity.RoleName;
import com.example.taskmanagement.entity.Permission;
import com.example.taskmanagement.entity.PermissionName;
import com.example.taskmanagement.repository.RoleRepository;
import com.example.taskmanagement.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

@Service
public class RoleService {
	@Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Role createRole(RoleName name, String displayName, String description) {
        if (roleRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Bu rol zaten var: " + name);
        }

        Role role = new Role(name, displayName, description);
        return roleRepository.save(role);
    }

    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public List<Role> findByUserId(Long userId) {
        return roleRepository.findByUserId(userId);
    }

    public Role assignPermission(RoleName roleName, Set<PermissionName> permissionNames) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + roleName));

        Set<Permission> permissions = new HashSet<>();
        for (PermissionName permissionName : permissionNames) {
            Permission permission = permissionRepository.findByName(permissionName)
                    .orElseThrow(() -> new RuntimeException("Yetki bulunamadı: " + permissionName));
            permissions.add(permission);
        }

        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    public Role removePermission(RoleName roleName, PermissionName permissionName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + roleName));

        Permission permission = permissionRepository.findByName(permissionName)
                .orElseThrow(() -> new RuntimeException("Yetki bulunamadı: " + permissionName));

        role.getPermissions().remove(permission);
        return roleRepository.save(role);
    }
}
