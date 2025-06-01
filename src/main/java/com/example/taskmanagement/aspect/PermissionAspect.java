package com.example.taskmanagement.aspect;

import com.example.taskmanagement.annotation.RequiresPermission;
import com.example.taskmanagement.annotation.RequiresRole;
import com.example.taskmanagement.service.RBACService;
import com.example.taskmanagement.entity.PermissionName;
import com.example.taskmanagement.entity.RoleName;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {
	@Autowired
    private RBACService rbacService;
    
    @Around("@annotation(requiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequiresPermission requiresPermission) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Kullanıcı kimlik doğrulaması yapılmamış");
        }
        
        String username = authentication.getName();
        Long userId = getUserIdByUsername(username); 
        
        PermissionName requiredPermission = requiresPermission.value();
        
        if (!rbacService.hasPermission(userId, requiredPermission)) {
            throw new SecurityException("Bu işlem için yetkiniz bulunmamaktadır: " + requiredPermission);
        }
        
        return joinPoint.proceed();
    }
    
    @Around("@annotation(requiresRole)")
    public Object checkRole(ProceedingJoinPoint joinPoint, RequiresRole requiresRole) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Kullanıcı kimlik doğrulaması yapılmamış");
        }
        
        String username = authentication.getName();
        Long userId = getUserIdByUsername(username);
        
        RoleName[] requiredRoles = requiresRole.value();
        
        boolean hasRole = false;
        for (RoleName roleName : requiredRoles) {
            if (checkUserHasRole(userId, roleName)) {
                hasRole = true;
                break;
            }
        }
        
        if (!hasRole) {
            throw new SecurityException("Bu işlem için gerekli role sahip değilsiniz");
        }
        
        return joinPoint.proceed();
    }
    
    private Long getUserIdByUsername(String username) {
        return 1L; 
    }
    
    private boolean checkUserHasRole(Long userId, RoleName roleName) {
        return true; 
    }
    
}