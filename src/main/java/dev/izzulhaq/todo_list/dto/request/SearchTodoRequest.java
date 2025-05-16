package dev.izzulhaq.todo_list.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchTodoRequest extends SearchRequest {
    private String title;
    private String status;
    private String todoDate;
    private String userId;
    private String categoryId;
}
