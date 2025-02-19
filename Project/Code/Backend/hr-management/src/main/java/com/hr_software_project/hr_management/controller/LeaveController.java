package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.dto.CreateLeaveRequestDTO;
import com.hr_software_project.hr_management.dto.LeaveBalanceDTO;
import com.hr_software_project.hr_management.dto.UpdateLeaveRequestDTO;
import com.hr_software_project.hr_management.entity.LeaveCarryForwardDO;
import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;


    @GetMapping("/getLeaveDetail")
    public LeaveDO getLeaveDetail(
            @RequestParam Long currentUserId,
            @RequestParam Long leaveId) {
        return leaveService.getLeaveDetail(currentUserId, leaveId);
    }

    @PostMapping("/createLeave")
    public LeaveDO createLeave(
            @RequestBody CreateLeaveRequestDTO req) {
        return leaveService.create(req);
    }

    @PutMapping("/updateLeave")
    public LeaveDO updateLeave(@RequestBody UpdateLeaveRequestDTO req) {
        return leaveService.update(req);
    }

    @GetMapping("/getAllLeaveByUser")
    public List<LeaveDO> getAllLeaveByUser(
            @RequestParam Long currentUserId) {
        return leaveService.getAllLeaveByUser(currentUserId);
    }

    @GetMapping("/getAllLeaveFilterByStatus")
    public List<LeaveDO> getAllLeaveFilterByStatus(
            @RequestParam Long currentUserId,
            @RequestParam String status) {
        return leaveService.getAllLeaveFilterByStatus(currentUserId, status);
    }

    @GetMapping("/getLeaveBalance")
    public List<LeaveBalanceDTO> getLeaveBalance(
            @RequestParam Long currentUserId) {
        return leaveService.getLeaveBalance(currentUserId);
    }

    @PostMapping("/carryForwardLeave")
    public List<LeaveCarryForwardDO> carryForwardLeave(
            @RequestParam Long currentUserId,
            @RequestParam Long userId,
            @RequestParam Integer year) {
        return leaveService.carryForwardLeave(currentUserId, userId, year);
    }

    @GetMapping("/getLeaveCarryForwardByYear")
    public List<LeaveCarryForwardDO> getLeaveCarryForwardByYear(
            @RequestParam Long currentUserId,
            @RequestParam Long userId,
            @RequestParam Integer year) {
        return leaveService.getLeaveCarryForwardByYear(currentUserId, userId, year);
    }




}
