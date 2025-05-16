package dev.izzulhaq.todo_list.repositories;

import dev.izzulhaq.todo_list.entities.TodoCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoCategoryRepository extends JpaRepository<TodoCategory, String> {
    List<TodoCategory> findByUserAccountId(String userId);
}
