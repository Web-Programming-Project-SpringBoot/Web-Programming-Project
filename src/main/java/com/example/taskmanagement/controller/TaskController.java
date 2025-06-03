package com.example.taskmanagement.controller;

import com.example.taskmanagement.annotation.RequiresPermission;
import com.example.taskmanagement.annotation.RequiresRole;
import com.example.taskmanagement.entity.PermissionName;
import com.example.taskmanagement.entity.RoleName;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @RequiresPermission(PermissionName.READ_TASK)
    public ResponseEntity<Map<String, Object>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görevler başarıyla getirildi");
        response.put("tasks", tasks);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @RequiresPermission(PermissionName.CREATE_TASK)
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görev başarıyla oluşturuldu");
        response.put("task", createdTask);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{taskId}")
    @RequiresPermission(PermissionName.UPDATE_TASK)
    public ResponseEntity<Map<String, Object>> updateTask(
            @PathVariable Long taskId,
            @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(taskId, task);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görev başarıyla güncellendi");
        response.put("taskId", taskId);
        response.put("task", updatedTask);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{taskId}")
    @RequiresPermission(PermissionName.DELETE_TASK)
    public ResponseEntity<Map<String, Object>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
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
        Long userId = Long.valueOf(assignmentData.get("userId").toString());
        Task assignedTask = taskService.assignTask(taskId, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Görev başarıyla atandı");
        response.put("taskId", taskId);
        response.put("task", assignedTask);
        return ResponseEntity.ok(response);
    }
}