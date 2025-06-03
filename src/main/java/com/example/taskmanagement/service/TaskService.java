package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.repository.TaskRepository;
import com.example.taskmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Dependency Injection
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Görev oluşturma
    public Task createTask(Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty");
        }
        return taskRepository.save(task);
    }

    // Tüm görevleri listeleme
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // ID ile görev bulma
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Görev güncelleme
    public Task updateTask(Long id, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setAssignedUser(updatedTask.getAssignedUser());
            return taskRepository.save(task);
        }
        throw new IllegalArgumentException("Task not found with id: " + id);
    }

    // Görev silme
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    // Görev atama
    public Task assignTask(Long taskId, Long userId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<User> userOptional = userRepository.findById(userId);
        
        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("Task not found with id: " + taskId);
        }
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        Task task = taskOptional.get();
        task.setAssignedUser(userOptional.get());
        return taskRepository.save(task);
    }
}