package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.OvertimeDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OvertimeRepository extends JpaRepository<OvertimeDO, Long> {

    List<OvertimeDO> findByUser_Id(Long userId);
}


