package dev.izzulhaq.todo_list.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePasswordRequest {
    private String currentPassword;
    private String newPassword;
}
