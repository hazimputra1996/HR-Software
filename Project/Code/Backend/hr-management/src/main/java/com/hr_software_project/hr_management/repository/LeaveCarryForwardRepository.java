package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.LeaveCarryForwardDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveCarryForwardRepository extends JpaRepository<LeaveCarryForwardDO, Long> {

    @Query("SELECT lcf FROM LeaveCarryForwardDO lcf WHERE lcf.user.id = :userId AND lcf.previousYear = :previousYear")

    List<LeaveCarryForwardDO> findByUser_Id_And_PreviousYear(Long userId, Integer previousYear);
}


