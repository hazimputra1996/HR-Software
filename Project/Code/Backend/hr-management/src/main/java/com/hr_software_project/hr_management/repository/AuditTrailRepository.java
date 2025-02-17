package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.AuditTrailDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepository extends JpaRepository<AuditTrailDO, Long> {
}

//