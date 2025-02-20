package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.enums.Role;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;
import com.hr_software_project.hr_management.repository.UserRepository;
import com.hr_software_project.hr_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        UserDO user = new UserDO();

        user.setRole(Role.valueOf(req.getRole()));
        user.setFirst_name(req.getFirstName());
        user.setLast_name(req.getLastName());

        userRepository.findByEmail(req.getEmail()).ifPresent(userDO -> {
            throw new ServiceException(ServiceErrorCodes.EMAIL_EXISTS);
        });

        if (userRepository.existsByUsername(req.getUsername())) {
            throw new ServiceException(ServiceErrorCodes.USERNAME_EXISTS);
        }

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setSalary(req.getSalary());
        user.setEpf_number(req.getEpfNumber());
        user.setSocso_number(req.getSocsoNumber());
        user.setIncome_tax_number(req.getIncomeTaxNumber());
        user.setLeave_balance(req.getLeaveBalance());

        try {
            user.setHire_date(new SimpleDateFormat("yyyy-MM-dd").parse(req.getHireDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        userRepository.findById(req.getSupervisorId()).ifPresentOrElse(
                user::setSupervisor,
                () -> {
                    throw new ServiceException(ServiceErrorCodes.SUPERVISOR_NOT_EXISTS);
                }
        );

        userRepository.save(user);
        return user;
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
