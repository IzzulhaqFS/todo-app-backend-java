package dev.izzulhaq.todo_list.specifications;

import dev.izzulhaq.todo_list.constants.Constant;
import dev.izzulhaq.todo_list.constants.TodoStatus;
import dev.izzulhaq.todo_list.dto.request.SearchTodoRequest;
import dev.izzulhaq.todo_list.entities.Todo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TodoSpecification {
    public static Specification<Todo> getSpecification(SearchTodoRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getUserId() != null) {
                Predicate userPredicate = criteriaBuilder.equal(root.get("userAccount").get("id"), request.getUserId());
                predicates.add(userPredicate);
            }

            if (request.getTodoDate() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_PATTERN);
                LocalDate date = LocalDate.parse(request.getTodoDate(), formatter);
                Predicate datePredicate = criteriaBuilder.equal(root.get("todoDate"), date);
                predicates.add(datePredicate);
            }

            if (request.getTitle() != null) {
                Predicate titlePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + request.getTitle().toLowerCase() + "%"
                );
                predicates.add(titlePredicate);
            }

            if (request.getStatus() != null) {
                TodoStatus status = TodoStatus.findByDescription(request.getStatus());
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), status);
                predicates.add(statusPredicate);
            }

            if (request.getCategoryId() != null) {
                Predicate categoryPredicate = criteriaBuilder.equal(
                        root.get("category").get("id"),
                        request.getCategoryId()
                );
                predicates.add(categoryPredicate);
            }

            assert query != null;
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
