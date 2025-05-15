package dev.izzulhaq.todo_list.repositories;

import dev.izzulhaq.todo_list.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String>, JpaSpecificationExecutor<Todo> {
}
