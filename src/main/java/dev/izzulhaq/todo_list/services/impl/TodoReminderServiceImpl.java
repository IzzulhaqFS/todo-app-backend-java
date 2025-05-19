package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.constants.TodoStatus;
import dev.izzulhaq.todo_list.entities.Todo;
import dev.izzulhaq.todo_list.repositories.TodoRepository;
import dev.izzulhaq.todo_list.services.TodoReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoReminderServiceImpl implements TodoReminderService {
    private final TodoRepository repository;

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void checkUpcomingDeadlines() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(12);

        List<Todo> upcomingTodos = repository.findByDeadlineBetween(start, end);

        if (!upcomingTodos.isEmpty()) {
            System.out.println("=== Near Deadline Todos ===");
            upcomingTodos.forEach(todo -> {
                if (todo.getStatus() == TodoStatus.ONGOING) {
                    updateStatus(todo, "near deadline");
                }
                System.out.println("Todo: " + todo.getTitle() +
                        " | Deadline: " + todo.getDeadline());
            });
            repository.saveAllAndFlush(upcomingTodos);
        }
    }

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void checkOverdue() {
        LocalDateTime now = LocalDateTime.now();

        List<Todo> overdueTodos = repository.findByStatusInAndDeadlineBefore(List.of(
                TodoStatus.ONGOING, TodoStatus.NEAR_DEADLINE),
                now
        );

        if (!overdueTodos.isEmpty()) {
            System.out.println("=== Overdue Todos ===");
            overdueTodos.forEach(todo -> {
                updateStatus(todo, "overdue");
                System.out.println("Todo: " + todo.getTitle() +
                        " | Deadline: " + todo.getDeadline());
            });
            repository.saveAllAndFlush(overdueTodos);
        }
    }

    private void updateStatus(Todo todo, String status) {
        TodoStatus newStatus = TodoStatus.findByDescription(status);
        todo.setStatus(newStatus);
        todo.setUpdatedAt(LocalDateTime.now());
    }
}
