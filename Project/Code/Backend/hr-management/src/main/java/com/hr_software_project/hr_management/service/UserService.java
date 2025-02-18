package com.hr_software_project.hr_management.service;

import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.entity.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {


    UserDO getUserByEmail(Long currentUserId, String email);
    UserDO createUser(CreateUserRequestDTO req);
    UserDO update(UserDO user);
    void delete(Long id);
    List<UserDO> getUsers(Long currentUserId);

    // Other CRUD operations
}
