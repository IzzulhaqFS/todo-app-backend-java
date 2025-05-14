package dev.izzulhaq.todo_list.services;

import dev.izzulhaq.todo_list.dto.request.AuthRequest;
import dev.izzulhaq.todo_list.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse adminRegister(AuthRequest request);
    AuthResponse userRegister(AuthRequest request);
    AuthResponse login(AuthRequest request);
}
