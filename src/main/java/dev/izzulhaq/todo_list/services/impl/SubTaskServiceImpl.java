package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.entities.SubTask;
import dev.izzulhaq.todo_list.repositories.SubTaskRepository;
import dev.izzulhaq.todo_list.services.SubTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubTaskServiceImpl implements SubTaskService {
    private final SubTaskRepository repository;

    @Override
    public void createBulk(List<SubTask> subTaskList) {
        repository.saveAllAndFlush(subTaskList);
    }

    @Override
    public List<SubTask> getAllByTodo(String todoId) {
        return repository.findByTodoId(todoId);
    }
}
