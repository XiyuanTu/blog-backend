package com.txy.blog.service.impl;

import com.txy.blog.entity.Role;
import com.txy.blog.entity.User;
import com.txy.blog.exception.BlogAPIException;
import com.txy.blog.exception.RoleNameNotFoundException;
import com.txy.blog.payload.LoginDTO;
import com.txy.blog.payload.RegisterDTO;
import com.txy.blog.repository.RoleRepository;
import com.txy.blog.repository.UserRepository;
import com.txy.blog.security.JwtTokenProvider;
import com.txy.blog.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        String username = registerDTO.getUsername();
        String email = registerDTO.getEmail();
        String name = registerDTO.getName();
        String password = registerDTO.getPassword();

        if (userRepository.existsByUsername(username)) {
            throw new BlogAPIException("Username already exists.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new BlogAPIException("Email already exists.");
        }

        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        String roleName = "ROLE_USER";
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RoleNameNotFoundException(roleName));
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);

        return "User registered successfully";
    }
}
