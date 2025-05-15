package dev.izzulhaq.todo_list.controllers;

import dev.izzulhaq.todo_list.constants.Constant;
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
@RequestMapping(path = Constant.AUTH_API)
public class AuthController {
    private final AuthService authService;

    @PostMapping(Constant.ADMIN_REGISTER)
    public ResponseEntity<CommonResponse<AuthResponse>> registerAdmin(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.adminRegister(request);
        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message(Constant.REGISTER_MESSAGE_ADMIN)
                .data(authResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(Constant.USER_REGISTER)
    public ResponseEntity<CommonResponse<AuthResponse>> registerUser(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.userRegister(request);
        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message(Constant.REGISTER_MESSAGE_USER)
                .data(authResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(Constant.LOGIN)
    public ResponseEntity<CommonResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.login(request);
        CommonResponse<AuthResponse> response = CommonResponse.<AuthResponse>builder()
                .status(HttpStatus.OK.value())
                .message(Constant.LOGIN_MESSAGE)
                .data(authResponse)
                .build();
        return ResponseEntity.ok(response);
    }
}
