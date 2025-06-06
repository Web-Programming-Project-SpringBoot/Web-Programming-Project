package com.example.taskmanagement.controller;


import com.example.taskmanagement.annotation.RequiresPermission;
import com.example.taskmanagement.annotation.RequiresRole;
import com.example.taskmanagement.entity.PermissionName;
import com.example.taskmanagement.entity.RoleName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @GetMapping
    @RequiresPermission(PermissionName.READ_TASK)
    public ResponseEntity<Map<String, Object>> getAllTasks() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görevler başarıyla getirildi");
        response.put("tasks", "Burada görev listesi olacak");
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    @RequiresPermission(PermissionName.CREATE_TASK)
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody Map<String, Object> taskData) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görev başarıyla oluşturuldu");
        response.put("task", taskData);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{taskId}")
    @RequiresPermission(PermissionName.UPDATE_TASK)
    public ResponseEntity<Map<String, Object>> updateTask(
            @PathVariable Long taskId,
            @RequestBody Map<String, Object> taskData) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görev başarıyla güncellendi");
        response.put("taskId", taskId);
        response.put("updatedData", taskData);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{taskId}")
    @RequiresPermission(PermissionName.DELETE_TASK)
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable Long taskId) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görev başarıyla silindi");
        response.put("taskId", taskId);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/{taskId}/assign")
    @RequiresRole({RoleName.ADMIN, RoleName.MANAGER, RoleName.PROJECT_LEAD})
    public ResponseEntity<Map<String, Object>> assignTask(
            @PathVariable Long taskId,
            @RequestBody Map<String, Object> assignmentData) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görev başarıyla atandı");
        response.put("taskId", taskId);
        response.put("assignmentData", assignmentData);
        return ResponseEntity.ok(response);
    }
}
