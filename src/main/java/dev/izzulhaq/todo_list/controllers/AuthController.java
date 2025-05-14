package dev.izzulhaq.todo_list.controllers;

import dev.izzulhaq.todo_list.dto.request.AuthRequest;
import dev.izzulhaq.todo_list.dto.response.AuthResponse;
import dev.izzulhaq.todo_list.dto.response.CommonResponse;
import dev.izzulhaq.todo_list.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register/admin")
    public ResponseEntity<CommonResponse<AuthResponse>> registerAdmin(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.adminRegister(request);
        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Successfully register new admin.")
                .data(authResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/register/user")
    public ResponseEntity<CommonResponse<AuthResponse>> registerUser(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.userRegister(request);
        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Successfully register new user.")
                .data(authResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.login(request);
        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Logged in successfully.")
                .data(authResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
