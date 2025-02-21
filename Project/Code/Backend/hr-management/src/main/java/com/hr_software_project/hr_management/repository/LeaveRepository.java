package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveDO, Long> {

    List<LeaveDO> findByUser_Id(Long userId);
}

