package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.SalaryStatementAllowanceDO;
import com.hr_software_project.hr_management.entity.SalaryStatementDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryStatementAllowanceRepository extends JpaRepository<SalaryStatementAllowanceDO, Long> {

    List<SalaryStatementAllowanceDO> findByUser_Id(Long userId);
}

