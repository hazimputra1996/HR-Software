package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.EmployerDeductionDO;
import com.hr_software_project.hr_management.entity.UserDeductionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployerDeductionRepository extends JpaRepository<EmployerDeductionDO, Long> {
    List<EmployerDeductionDO> findByUser_Id(Long userId);
}