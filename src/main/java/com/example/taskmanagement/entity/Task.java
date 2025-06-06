package com.example.taskmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String status; // Örneğin: "TODO", "IN_PROGRESS", "DONE"

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignedUser; // Görevin atandığı kullanıcı
}