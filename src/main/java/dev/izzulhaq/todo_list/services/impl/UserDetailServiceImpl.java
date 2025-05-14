package dev.izzulhaq.todo_list.services.impl;

import dev.izzulhaq.todo_list.entities.UserAccount;
import dev.izzulhaq.todo_list.repositories.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserAccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User account not found."));
        return new User(
                userAccount.getUsername(),
                userAccount.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(userAccount.getRole().name())
                )
        );
    }
}
