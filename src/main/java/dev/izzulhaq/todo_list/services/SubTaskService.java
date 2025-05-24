package dev.izzulhaq.todo_list.services;

import dev.izzulhaq.todo_list.entities.SubTask;

import java.util.List;

public interface SubTaskService {
    void createBulk(List<SubTask> subTaskList);
    List<SubTask> getAllByTodo(String todoId);
}
