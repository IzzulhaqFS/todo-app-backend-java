package dev.izzulhaq.todo_list.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.izzulhaq.todo_list.constants.Constant;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {
    private String id;
    private String userId;

    @JsonFormat(pattern = Constant.DATE_PATTERN)
    private LocalDate todoDate;

    private String title;
    private String description;
    private String status;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime updatedAt;
}
