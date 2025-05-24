package dev.izzulhaq.todo_list.utils;

import java.util.List;
import java.util.function.Function;

public class SafeMapListHelper {
    public static <T, R>List<R> safeMapList(List<T> list, Function<T, R> mapper) {
        return list == null ? List.of() : list.stream().map(mapper).toList();
    }
}
