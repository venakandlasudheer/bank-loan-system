package com.loan.user_service.service;

import com.loan.user_service.dto.AuthResponse;
import com.loan.user_service.dto.LoginRequest;
import com.loan.user_service.dto.RegisterRequest;
import com.loan.user_service.model.User;
import com.loan.user_service.repository.UserRepository;
import com.loan.user_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already taken!";
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered!";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");

        userRepository.save(user);
        return "User registered successfully!";
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new AuthResponse(token, user.getRole(), user.getUsername());
    }
}