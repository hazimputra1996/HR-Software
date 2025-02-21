package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.config.JwtTokenUtil;
import com.hr_software_project.hr_management.dto.RegisterRequestDTO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequest) {
        // Validate user details (e.g., check if username is already taken)
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();

        if (authService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        // Hash the password before saving
        registerRequest.setPassword(passwordEncoder.encode(password));

        // Save user to the database
        authService.saveUser(registerRequest);

        // Return success response
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RegisterRequestDTO.LoginRequest loginRequest) {
        // Authenticate user (e.g., check username and password) need frontend to encode also
        UserDO user = authService.findByUsername(loginRequest.getUsername());
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        // Generate JWT token
        String token = jwtTokenUtil.generateToken(loginRequest.getUsername());

        // Return token to client
        return ResponseEntity.ok(new RegisterRequestDTO.JwtResponse(token));
    }
}
