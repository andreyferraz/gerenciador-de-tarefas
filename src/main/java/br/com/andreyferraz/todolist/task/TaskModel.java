package br.com.andreyferraz.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity (name = "db_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private UUID userId;
    private String description;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String priority;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
