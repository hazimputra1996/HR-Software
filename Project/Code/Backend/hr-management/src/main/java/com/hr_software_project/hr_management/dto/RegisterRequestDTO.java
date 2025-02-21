package com.hr_software_project.hr_management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String password;
    // Add other fields as needed


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JwtResponse {
        private String token;
    }
}