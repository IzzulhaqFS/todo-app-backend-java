package dev.izzulhaq.todo_list.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoCategoryResponse {
    private String id;
    private String name;
    private String userId;
}
