package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.SalaryStatementDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryStatementRepository extends JpaRepository<SalaryStatementDO, Long> {
}

