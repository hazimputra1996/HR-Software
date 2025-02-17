package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public UserDO registerUser(@RequestBody UserDO user) {
//        return userService.save(user);
//    }

//    @PostMapping("/login")
//    public String loginUser(@RequestBody LoginRequest loginRequest) {
//        // Check credentials and return JWT token
//    }
}
