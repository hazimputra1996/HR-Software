package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDO, Long> {
    Optional<UserDO> findByEmail(String email);  // Custom query to find users by email
    boolean existsByUsername(String username);
    UserDO findByUsername(String username);
}