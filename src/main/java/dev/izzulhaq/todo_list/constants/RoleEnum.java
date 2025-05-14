package dev.izzulhaq.todo_list.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN("admin"),
    ROLE_USER("user");

    private final String description;

    public static RoleEnum findByDescription(String description) {
        for (RoleEnum role : values()) {
            if (role.description.equalsIgnoreCase(description)) {
                return role;
            }
        }
        return null;
    }
}
