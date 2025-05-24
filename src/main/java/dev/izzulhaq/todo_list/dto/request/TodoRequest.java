package dev.izzulhaq.todo_list.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequest {

    @NotBlank(message = "User Account Id is required.")
    private String userId;

    @NotBlank(message = "User Account Id is required.")
    private String title;

    @NotBlank(message = "User Account Id is required.")
    private String deadline;

    private String description;

    private String categoryId;
    private String priority;
    private List<SubTaskRequest> subTasks;
}
