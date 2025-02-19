package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserDetailByEmail")
    public UserDO getUserByEmail(
            @RequestParam String email,
            @RequestParam Long currentUserId) {
        return userService.getUserByEmail(currentUserId, email);
    }

    @PostMapping("/createUser")
    public UserDO createUser(
            @RequestBody CreateUserRequestDTO req) {
        return userService.createUser(req);
    }

    @PutMapping("/updateUser")
    public UserDO updateUser(@RequestBody UserDO user) {
        return userService.update(user);
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam Long id) {
        userService.delete(id);
    }

    @GetMapping("/getUsersDetail")
    public List<UserDO> getUsers(
            @RequestParam Long currentUserId) {
        return userService.getUsers(currentUserId);
    }


}
