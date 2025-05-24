package dev.izzulhaq.todo_list.repositories;

import dev.izzulhaq.todo_list.entities.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, String> {
    List<SubTask> findByTodoId(String todoId);
}
