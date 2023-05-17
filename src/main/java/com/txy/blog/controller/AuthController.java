package com.txy.blog.controller;

import com.txy.blog.payload.JWTAuthResponse;
import com.txy.blog.payload.LoginDTO;
import com.txy.blog.payload.RegisterDTO;
import com.txy.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = {"/login", "/sign-in"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDTO loginDTO) {
        String token = authService.login(loginDTO);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> login(@RequestBody RegisterDTO registerDTO) {
        String response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
