package com.txy.blog.controller;

import com.txy.blog.payload.JWTAuthResponse;
import com.txy.blog.payload.LoginDTO;
import com.txy.blog.payload.RegisterDTO;
import com.txy.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/v1/auth/login", "/v1/auth/sign-in"})
    @Operation(summary = "Login")
    @ApiResponse(responseCode = "200", description = "Login successfully")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/v1/auth/register", "/v1/auth/signup"})
    @Operation(summary = "Register")
    @ApiResponse(responseCode = "200", description = "Register successfully")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
