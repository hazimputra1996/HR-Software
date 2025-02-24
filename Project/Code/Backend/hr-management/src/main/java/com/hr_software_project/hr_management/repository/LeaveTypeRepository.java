package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.LeaveTypeDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveTypeRepository extends JpaRepository<LeaveTypeDO, Long> {

    List<LeaveTypeDO> findByName(String leaveType);
}