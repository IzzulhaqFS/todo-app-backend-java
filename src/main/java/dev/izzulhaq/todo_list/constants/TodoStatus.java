package dev.izzulhaq.todo_list.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TodoStatus {
    FINISHED("finished"),
    CANCELED("canceled"),
    RESCHEDULED("rescheduled"),
    ONGOING("ongoing");

    private final String description;

    public static TodoStatus findByDescription(String description) {
        for (TodoStatus status : values()) {
            if (status.description.equalsIgnoreCase(description)) {
                return status;
            }
        }
        return null;
    }
}
