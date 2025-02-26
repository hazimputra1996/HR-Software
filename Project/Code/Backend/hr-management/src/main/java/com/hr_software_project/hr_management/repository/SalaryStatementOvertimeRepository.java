package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.SalaryStatementOvertimeDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryStatementOvertimeRepository extends JpaRepository<SalaryStatementOvertimeDO, Long> {

    List<SalaryStatementOvertimeDO> findByUser_Id(Long userId);
}

