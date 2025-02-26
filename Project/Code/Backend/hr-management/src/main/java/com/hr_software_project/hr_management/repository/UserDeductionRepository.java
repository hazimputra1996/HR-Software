package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.UserDeductionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeductionRepository extends JpaRepository<UserDeductionDO, Long> {
    List<UserDeductionDO> findByUser_Id(Long userId);
}