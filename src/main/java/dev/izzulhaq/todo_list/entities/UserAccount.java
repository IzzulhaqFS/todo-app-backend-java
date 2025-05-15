package dev.izzulhaq.todo_list.entities;

import dev.izzulhaq.todo_list.constants.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_user")
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, updatable = false)
    private RoleEnum role;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "userAccount")
    private List<Todo> todoList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleEnum> roles = List.of(role);
        return roles.stream().map(roleEnum -> new SimpleGrantedAuthority(roleEnum.name())).toList();
    }
}
