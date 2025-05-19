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
    private UserAccountResponse user;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime deadline;

    private String title;
    private String description;
    private String status;
    private String category;
    private String priority;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime updatedAt;
}
