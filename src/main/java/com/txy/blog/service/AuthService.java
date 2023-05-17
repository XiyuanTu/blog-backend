package com.txy.blog.service;

import com.txy.blog.payload.LoginDTO;
import com.txy.blog.payload.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}
