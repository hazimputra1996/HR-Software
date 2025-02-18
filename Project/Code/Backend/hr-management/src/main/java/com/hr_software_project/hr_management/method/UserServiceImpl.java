package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.repository.UserRepository;
import com.hr_software_project.hr_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDO getUserByEmail(Long currentUserId, String email) {
        return userRepository.findByEmail(email).get();
    }

    public UserDO createUser(CreateUserRequestDTO req) {
        return null;
    }

    public UserDO save(UserDO user) {
        return userRepository.save(user);
    }

    public UserDO update(UserDO user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserDO> getUsers(Long currentUserId) {
        return userRepository.findAll();
    }

}
