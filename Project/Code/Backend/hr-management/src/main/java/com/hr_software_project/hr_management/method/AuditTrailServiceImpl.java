package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.CreateAuditTrailRequestDTO;
import com.hr_software_project.hr_management.dto.CreateLeaveRequestDTO;
import com.hr_software_project.hr_management.dto.LeaveBalanceDTO;
import com.hr_software_project.hr_management.dto.UpdateLeaveRequestDTO;
import com.hr_software_project.hr_management.entity.AuditTrailDO;
import com.hr_software_project.hr_management.entity.LeaveCarryForwardDO;
import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.repository.AuditTrailRepository;
import com.hr_software_project.hr_management.repository.LeaveRepository;
import com.hr_software_project.hr_management.service.AuditTrailService;
import com.hr_software_project.hr_management.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class AuditTrailServiceImpl implements AuditTrailService {

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    public List<AuditTrailDO> getAuditTrailAll(Long currentUserId){
        return null;
    }

    public List<AuditTrailDO> getAuditTrailForUser(Long currentUserId, Long userId){
        return null;
    }

    public AuditTrailDO createAuditTrail(CreateAuditTrailRequestDTO req){
        return null;
    }


}
