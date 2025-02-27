package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.CompanyDO;
import com.hr_software_project.hr_management.entity.SalaryStatementAllowanceDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<CompanyDO, Long> {
}

