package dev.izzulhaq.todo_list.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonResponse<T> {
    private Integer status;
    private String message;
    private T data;
    private PagingResponse paging;
}
