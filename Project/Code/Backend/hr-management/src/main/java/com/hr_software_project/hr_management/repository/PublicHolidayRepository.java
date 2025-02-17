package com.hr_software_project.hr_management.repository;

import com.hr_software_project.hr_management.entity.PublicHolidayDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicHolidayRepository extends JpaRepository<PublicHolidayDO, Long> {
}

