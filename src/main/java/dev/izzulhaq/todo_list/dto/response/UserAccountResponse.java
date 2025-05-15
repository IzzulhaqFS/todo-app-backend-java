package dev.izzulhaq.todo_list.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.izzulhaq.todo_list.constants.Constant;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountResponse {
    private String id;
    private String username;
    private String role;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = Constant.DATE_TIME_PATTERN)
    private LocalDateTime updatedAt;
    private Boolean isActive;
}
