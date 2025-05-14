package dev.izzulhaq.todo_list.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SearchRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDirection;

    public Integer getPage() {
        return (page <= 0 ? 0 : page - 1);
    }
}
