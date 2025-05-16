package dev.izzulhaq.todo_list.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCategoryRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "User Id is required.")
    private String userId;
}
