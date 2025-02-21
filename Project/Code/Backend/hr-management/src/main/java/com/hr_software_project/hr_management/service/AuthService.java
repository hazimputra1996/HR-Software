package com.hr_software_project.hr_management.service;

import com.hr_software_project.hr_management.dto.RegisterRequestDTO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(RegisterRequestDTO registerRequest) {
        UserDO user = new UserDO();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());  // Password should be hashed

        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserDO findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
