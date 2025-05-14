package dev.izzulhaq.todo_list.specifications;

import dev.izzulhaq.todo_list.constants.RoleEnum;
import dev.izzulhaq.todo_list.dto.request.SearchUserAccountRequest;
import dev.izzulhaq.todo_list.entities.UserAccount;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserAccountSpecification {
    public static Specification<UserAccount> getSpecification(SearchUserAccountRequest request){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates  = new ArrayList<>();

            if (request.getUsername() != null) {
                Predicate usernamePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("username")),
                        "%" + request.getUsername().toLowerCase() + "%"
                );
                predicates.add(usernamePredicate);
            }

            if (request.getRole() != null) {
                Predicate rolePredicate = criteriaBuilder.equal(
                        root.get("role"),
                        RoleEnum.findByDescription(request.getRole())
                );
                predicates.add(rolePredicate);
            }

            if (request.getIsActive() != null) {
                Predicate isActivePredicate = criteriaBuilder.equal(root.get("isActive"), request.getIsActive());
                predicates.add(isActivePredicate);
            }

            if (request.getCreatedDateStart() != null && request.getCreatedDateEnd() != null) {
                LocalDateTime start = LocalDateTime.parse(request.getCreatedDateStart());
                LocalDateTime end = LocalDateTime.parse(request.getCreatedDateEnd());
                Predicate createdDateRangePredicate = criteriaBuilder.between(root.get("createdAt"), start, end);
                predicates.add(createdDateRangePredicate);
            }

            if (request.getUpdatedDateStart() != null && request.getUpdatedDateEnd() != null) {
                LocalDateTime start = LocalDateTime.parse(request.getUpdatedDateStart());
                LocalDateTime end = LocalDateTime.parse(request.getUpdatedDateEnd());
                Predicate updatedDateRangePredicate = criteriaBuilder.between(root.get("updatedAt"), start, end);
                predicates.add(updatedDateRangePredicate);
            }

            assert query != null;
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
