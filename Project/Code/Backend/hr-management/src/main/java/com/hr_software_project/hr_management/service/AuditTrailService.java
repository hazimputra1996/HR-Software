package com.hr_software_project.hr_management.service;

import com.hr_software_project.hr_management.dto.CreateAuditTrailRequestDTO;
import com.hr_software_project.hr_management.dto.CreateLeaveRequestDTO;
import com.hr_software_project.hr_management.dto.LeaveBalanceDTO;
import com.hr_software_project.hr_management.dto.UpdateLeaveRequestDTO;
import com.hr_software_project.hr_management.entity.AuditTrailDO;
import com.hr_software_project.hr_management.entity.LeaveCarryForwardDO;
import com.hr_software_project.hr_management.entity.LeaveDO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface AuditTrailService {

    List<AuditTrailDO> getAuditTrailAll(Long currentUserId);
    List<AuditTrailDO> getAuditTrailForUser(Long currentUserId, Long userId);
    AuditTrailDO createAuditTrail(CreateAuditTrailRequestDTO req);



}
