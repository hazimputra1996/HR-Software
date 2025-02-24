package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.enums.LeaveStatus;
import com.hr_software_project.hr_management.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveDO, Long> {

    List<LeaveDO> findByUser_Id(Long userId);
    @Query("SELECT l FROM LeaveDO l WHERE l.user.id = :userId AND (l.startDate BETWEEN :startDate AND :endDate OR l.endDate BETWEEN :startDate AND :endDate) AND l.status = com.hr_software_project.hr_management.enums.LeaveStatus.APPROVED")
    List<LeaveDO> findByUser_Id_And_Between_Date(@Param("userId") Long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}

