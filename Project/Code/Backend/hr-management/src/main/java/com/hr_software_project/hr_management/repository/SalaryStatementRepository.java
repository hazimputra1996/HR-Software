package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.AuditTrailDO;
import com.hr_software_project.hr_management.entity.SalaryStatementDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryStatementRepository extends JpaRepository<SalaryStatementDO, Long> {

    List<SalaryStatementDO> findByUser_Id(Long userId);
}

