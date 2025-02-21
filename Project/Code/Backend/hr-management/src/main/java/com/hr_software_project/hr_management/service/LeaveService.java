package com.hr_software_project.hr_management.service;

import com.hr_software_project.hr_management.dto.CreateLeaveRequestDTO;
import com.hr_software_project.hr_management.dto.CreateUserRequestDTO;
import com.hr_software_project.hr_management.dto.LeaveBalanceDTO;
import com.hr_software_project.hr_management.dto.UpdateLeaveRequestDTO;
import com.hr_software_project.hr_management.entity.LeaveCarryForwardDO;
import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.entity.UserDO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface LeaveService {


    LeaveDO getLeaveDetail(Long currentUserId, Long leaveId);
    LeaveDO create(CreateLeaveRequestDTO req);
    LeaveDO update(LeaveDO req, Long currentUserId);
    List<LeaveDO> getAllLeaveByUser(Long currentUserId);

    List<LeaveDO> getAllLeaveFilterByStatus(Long currentUserId, String status, Boolean allUser);
    List<LeaveBalanceDTO> getLeaveBalance(Long currentUserId);
    List<LeaveCarryForwardDO> carryForwardLeave(Long currentUserId, Long userId, Integer year);
    List<LeaveCarryForwardDO> getLeaveCarryForwardByYear(Long currentUserId, Long userId, Integer year);


}
