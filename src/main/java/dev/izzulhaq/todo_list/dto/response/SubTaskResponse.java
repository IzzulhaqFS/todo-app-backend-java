package dev.izzulhaq.todo_list.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubTaskResponse {
    private String id;
    private String todoId;
    private String name;
    private String description;
    private Boolean isCompleted;
}
