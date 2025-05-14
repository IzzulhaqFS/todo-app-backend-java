package dev.izzulhaq.todo_list.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchUserAccountRequest extends SearchRequest {
    private String username;
    private String role;
    private String createdDateStart;
    private String createdDateEnd;
    private String updatedDateStart;
    private String updatedDateEnd;
    private Boolean isActive;
}
