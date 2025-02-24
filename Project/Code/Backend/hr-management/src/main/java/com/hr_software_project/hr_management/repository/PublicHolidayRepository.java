package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.PublicHolidayDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PublicHolidayRepository extends JpaRepository<PublicHolidayDO, Long> {

    @Query("SELECT ph FROM PublicHolidayDO ph WHERE YEAR(ph.date) = :year")
    List<PublicHolidayDO> findByYear(@Param("year") Integer year);

    @Query("SELECT ph FROM PublicHolidayDO ph WHERE ph.date BETWEEN :startDate AND :endDate")
    List<PublicHolidayDO> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

