package dev.izzulhaq.todo_list.specifications;

import dev.izzulhaq.todo_list.constants.Constant;
import dev.izzulhaq.todo_list.constants.RoleEnum;
import dev.izzulhaq.todo_list.dto.request.SearchUserAccountRequest;
import dev.izzulhaq.todo_list.entities.UserAccount;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constant.DATE_PATTERN);
                LocalDate startDate = LocalDate.parse(request.getCreatedDateStart(), dateFormatter);
                LocalDate endDate = LocalDate.parse(request.getCreatedDateEnd(), dateFormatter);

                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end = endDate.atTime(LocalTime.MAX);

                Predicate createdDateRangePredicate = criteriaBuilder.between(root.get("createdAt"), start, end);
                predicates.add(createdDateRangePredicate);
            }

            if (request.getUpdatedDateStart() != null && request.getUpdatedDateEnd() != null) {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constant.DATE_PATTERN);
                LocalDate startDate = LocalDate.parse(request.getUpdatedDateStart(), dateFormatter);
                LocalDate endDate = LocalDate.parse(request.getUpdatedDateEnd(), dateFormatter);

                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end = endDate.atTime(LocalTime.MAX);

                Predicate updatedDateRangePredicate = criteriaBuilder.between(root.get("updatedAt"), start, end);
                predicates.add(updatedDateRangePredicate);
            }

            assert query != null;
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
