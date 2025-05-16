package dev.izzulhaq.todo_list.repositories;

import dev.izzulhaq.todo_list.entities.TodoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoCategoryRepository extends JpaRepository<TodoCategory, String> {
}
