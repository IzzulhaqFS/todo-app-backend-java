package dev.izzulhaq.todo_list.repositories;

import dev.izzulhaq.todo_list.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends
        JpaRepository<UserAccount, String>,
        JpaSpecificationExecutor<UserAccount> {
    Optional<UserAccount> findByUsername(String username);
}
