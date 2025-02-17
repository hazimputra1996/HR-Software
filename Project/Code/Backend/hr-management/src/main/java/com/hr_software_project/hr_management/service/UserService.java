package com.hr_software_project.hr_management.service;

import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDO getUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    // Other CRUD operations
}
