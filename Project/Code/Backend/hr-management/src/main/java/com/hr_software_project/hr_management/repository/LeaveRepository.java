package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.LeaveDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<LeaveDO, Long> {
}

