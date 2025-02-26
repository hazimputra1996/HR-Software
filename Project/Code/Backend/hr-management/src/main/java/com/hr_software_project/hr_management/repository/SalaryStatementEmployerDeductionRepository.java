package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.SalaryStatementEmployeeDeductionDO;
import com.hr_software_project.hr_management.entity.SalaryStatementEmployerDeductionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryStatementEmployerDeductionRepository extends JpaRepository<SalaryStatementEmployerDeductionDO, Long> {

    List<SalaryStatementEmployerDeductionDO> findByUser_Id(Long userId);
}

