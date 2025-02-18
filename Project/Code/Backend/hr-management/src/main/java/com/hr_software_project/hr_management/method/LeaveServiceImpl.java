package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.CreateLeaveRequestDTO;
import com.hr_software_project.hr_management.dto.LeaveBalanceDTO;
import com.hr_software_project.hr_management.dto.UpdateLeaveRequestDTO;
import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.repository.LeaveRepository;
import com.hr_software_project.hr_management.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public LeaveDO getLeaveDetail(Long currentUserId, Long leaveId){
        return null;
    }

    public LeaveDO create(CreateLeaveRequestDTO req){
        return null;
    }

    public LeaveDO update(UpdateLeaveRequestDTO req){
        return null;
    }

    public List<LeaveDO> getAllLeaveByUser(Long currentUserId){
        return null;
    }

    public List<LeaveDO> getAllLeaveFilterByStatus(Long currentUserId, String status){
        return null;
    }

    public List<LeaveBalanceDTO> getLeaveBalance(Long currentUserId){
        return null;
    }



}
