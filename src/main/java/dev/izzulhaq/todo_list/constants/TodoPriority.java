package dev.izzulhaq.todo_list.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoPriority {
    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");

    private final String description;

    public static TodoPriority findByDescription(String description) {
        for (TodoPriority priority : values()) {
            if (priority.description.equalsIgnoreCase(description)) {
                return priority;
            }
        }
        return null;
    }
}
