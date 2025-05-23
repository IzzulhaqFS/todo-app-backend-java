package dev.izzulhaq.todo_list.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubTaskRequest {
    @NotBlank(message = "Name is required.")
    private String name;
    private String description;
}
