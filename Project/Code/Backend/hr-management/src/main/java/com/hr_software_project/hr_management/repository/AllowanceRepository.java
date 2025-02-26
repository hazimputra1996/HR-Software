package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.AllowanceDO;
import com.hr_software_project.hr_management.entity.DeductionDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllowanceRepository extends JpaRepository<AllowanceDO, Long> {

    List<AllowanceDO> findByUser_Id(Long userId);
}


