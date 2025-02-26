package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.SalaryStatementEmployeeDeductionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryStatementEmployeeDeductionRepository extends JpaRepository<SalaryStatementEmployeeDeductionDO, Long> {

    List<SalaryStatementEmployeeDeductionDO> findByUser_Id(Long userId);
}

