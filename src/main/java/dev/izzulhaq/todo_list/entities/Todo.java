package dev.izzulhaq.todo_list.entities;

import dev.izzulhaq.todo_list.constants.TodoPriority;
import dev.izzulhaq.todo_list.constants.TodoStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TodoStatus status;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority", nullable = false)
    private TodoPriority priority;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserAccount userAccount;

    @ManyToOne
    @JoinColumn(name = "todo_category_id")
    private TodoCategory category;

    @OneToMany(mappedBy = "todo")
    private List<SubTask> subTasks;
}
