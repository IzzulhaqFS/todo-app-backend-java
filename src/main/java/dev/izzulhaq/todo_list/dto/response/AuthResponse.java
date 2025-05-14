package dev.izzulhaq.todo_list.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String username;
    private String role;
    private String token;
}
