package dev.izzulhaq.todo_list.utils;

import dev.izzulhaq.todo_list.dto.response.SubTaskResponse;
import dev.izzulhaq.todo_list.dto.response.TodoCategoryResponse;
import dev.izzulhaq.todo_list.dto.response.TodoResponse;
import dev.izzulhaq.todo_list.dto.response.UserAccountResponse;
import dev.izzulhaq.todo_list.entities.SubTask;
import dev.izzulhaq.todo_list.entities.Todo;
import dev.izzulhaq.todo_list.entities.TodoCategory;
import dev.izzulhaq.todo_list.entities.UserAccount;

public class MapperUtil {
    public static TodoResponse mapToTodoResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .deadline(todo.getDeadline())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .status(todo.getStatus().name())
                .user(mapToUserAccountResponse(todo.getUserAccount()))
                .subTasks(SafeMapListHelper.safeMapList(todo.getSubTasks(), MapperUtil::mapToSubTaskResponse))
                .category(todo.getCategory().getName())
                .priority(todo.getPriority().name())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }

    public static UserAccountResponse mapToUserAccountResponse(UserAccount userAccount) {
        return UserAccountResponse.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .role(userAccount.getRole().name())
                .isActive(userAccount.getIsActive())
                .createdAt(userAccount.getCreatedAt())
                .updatedAt(userAccount.getUpdatedAt())
                .build();
    }

    public static SubTaskResponse mapToSubTaskResponse(SubTask subTask) {
        return SubTaskResponse.builder()
                .id(subTask.getId())
                .todoId(subTask.getTodo().getId())
                .name(subTask.getName())
                .description(subTask.getDescription())
                .isCompleted(subTask.getIsCompleted())
                .build();
    }

    public static TodoCategoryResponse mapToTodoCategoryResponse(TodoCategory todoCategory) {
        return TodoCategoryResponse.builder()
                .id(todoCategory.getId())
                .name(todoCategory.getName())
                .user(mapToUserAccountResponse(todoCategory.getUserAccount()))
                .build();
    }
}
