package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.CreateAuditTrailRequestDTO;
import com.hr_software_project.hr_management.entity.AuditTrailDO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.enums.Role;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;
import com.hr_software_project.hr_management.repository.AuditTrailRepository;
import com.hr_software_project.hr_management.service.AuditTrailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Transactional
@Service
@RequiredArgsConstructor
public class AuditTrailServiceImpl implements AuditTrailService {

    @Autowired
    private AuditTrailRepository auditTrailRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    public List<AuditTrailDO> getAuditTrailAll(Long currentUserId){

        UserDO user = userServiceImpl.getUserById(currentUserId);

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

        else return auditTrailRepository.findAll();
    }

    public List<AuditTrailDO> getAuditTrailForUser(Long currentUserId, Long userId){
        UserDO user = userServiceImpl.getUserById(currentUserId);

        if (!user.getRole().equals(Role.ADMIN) && !userId.equals(currentUserId)) {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

        else {
            return auditTrailRepository.findByUser_Id(userId);
        }
    }

    public AuditTrailDO createAuditTrail(CreateAuditTrailRequestDTO req){

        UserDO user = userServiceImpl.getUserById(req.getUserId());
        AuditTrailDO auditTrailDO = new AuditTrailDO();

        auditTrailDO.setUser(user);
        auditTrailDO.setActionBy(user);
        auditTrailDO.setAction(req.getAction());
        auditTrailDO.setModule(req.getModule());
        auditTrailDO.setOldValue(req.getOldValue());
        auditTrailDO.setNewValue(req.getNewValue());
        auditTrailDO.setActionTimestamp(new Date());
        auditTrailDO.setRemarks(req.getRemarks());
        auditTrailRepository.save(auditTrailDO);
        return auditTrailDO;
    }


}
