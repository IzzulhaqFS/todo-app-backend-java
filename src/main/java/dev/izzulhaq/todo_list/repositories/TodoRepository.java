package dev.izzulhaq.todo_list.repositories;

import dev.izzulhaq.todo_list.constants.TodoStatus;
import dev.izzulhaq.todo_list.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String>, JpaSpecificationExecutor<Todo> {
    List<Todo> findByUserAccountId(String userId);
    List<Todo> findByDeadlineBetween(LocalDateTime start, LocalDateTime end);
    List<Todo> findByStatusInAndDeadlineBefore(List<TodoStatus> statusList, LocalDateTime now);
}
