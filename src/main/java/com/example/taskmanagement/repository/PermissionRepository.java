package com.example.taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.taskmanagement.entity.Permission;
import com.example.taskmanagement.entity.PermissionName;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(PermissionName name);
}
