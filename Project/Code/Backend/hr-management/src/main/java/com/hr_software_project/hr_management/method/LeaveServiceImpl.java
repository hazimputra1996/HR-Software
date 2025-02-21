package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.CreateLeaveRequestDTO;
import com.hr_software_project.hr_management.dto.LeaveBalanceDTO;
import com.hr_software_project.hr_management.entity.LeaveCarryForwardDO;
import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.entity.LeaveTypeDO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.enums.LeaveStatus;
import com.hr_software_project.hr_management.enums.Role;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;
import com.hr_software_project.hr_management.repository.LeaveRepository;
import com.hr_software_project.hr_management.repository.LeaveTypeRepository;
import com.hr_software_project.hr_management.repository.UserRepository;
import com.hr_software_project.hr_management.service.LeaveService;
import com.hr_software_project.hr_management.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Transactional
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    public LeaveDO getLeaveDetail(Long currentUserId, Long leaveId){

        LeaveDO leave = leaveRepository.findById(leaveId).get();

        UserDO leaveUser = leave.getUser();
        UserDO currentUser = userRepository.getById(currentUserId);

        if (currentUser == null || !currentUser.getRole().equals(Role.ADMIN) || !currentUser.getId().equals(leaveUser.getSupervisor().getId()) || !leaveUser.getId().equals(currentUser.getId())){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        } else {
            return leave;
        }
    }

    public LeaveDO create(CreateLeaveRequestDTO req){

        if (!req.getUserId().equals(req.getCurrentUserId())){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
        UserDO userDO = userRepository.getById(req.getUserId());

        LeaveDO leaveDO = new LeaveDO();

        leaveDO.setLeaveType(req.getLeaveType());
        leaveDO.setAppliedOn(new Date());
        leaveDO.setUser(userDO);
        leaveDO.setAutoApproved(req.getAutoApproved());

        try {
            leaveDO.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(req.getStartDate()));
            leaveDO.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(req.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ServiceException(ServiceErrorCodes.WRONG_DATE_FORMAT);
        }

        if (req.getAutoApproved()){
            leaveDO.setStatus(LeaveStatus.APPROVED);
        } else {
            leaveDO.setStatus(LeaveStatus.PENDING);
        }

        return leaveRepository.save(leaveDO);
    }

    public LeaveDO update(LeaveDO req, Long currentUserId) {

        LeaveDO leave = leaveRepository.findById(req.getId()).get();

        UserDO leaveUser = leave.getUser();
        UserDO currentUser = userRepository.getById(currentUserId);

        if (currentUser == null || !currentUser.getId().equals(leaveUser.getSupervisor().getId())){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        } else {
            return leaveRepository.save(leave);
        }
    }

    public List<LeaveDO> getAllLeaveByUser(Long currentUserId){
        UserDO currentUser = userRepository.getById(currentUserId);

        if (currentUser == null || !currentUser.getRole().equals(Role.ADMIN)){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        } else {
            return leaveRepository.findAll();
        }
    }

    public List<LeaveDO> getAllLeaveFilterByStatus(Long currentUserId, String status, Boolean allUser){
        UserDO currentUser = userRepository.getById(currentUserId);

        List<LeaveDO> allLeave = leaveRepository.findAll();
        Set<LeaveDO> leaveSet = new HashSet<>();

        if (allUser && currentUser != null && !currentUser.getRole().equals(Role.ADMIN)){

            leaveSet.addAll(allLeave.stream()
            .filter(leaveDO -> leaveDO.getStatus().toString().equals(status))
                    .collect(Collectors.toSet()));
        } else {
            leaveSet.addAll(allLeave.stream()
                    .filter(leaveDO -> leaveDO.getStatus().toString().equals(status))
                    .filter(leaveDO -> leaveDO.getUser().getSupervisor().getId().equals(currentUserId))
                    .collect(Collectors.toSet()));
        }

        return new ArrayList<>(leaveSet);
    }

    public List<LeaveBalanceDTO> getLeaveBalance(Long currentUserId){
        List<LeaveBalanceDTO> response = new ArrayList<>();

        List<LeaveTypeDO> leaveType = leaveTypeRepository.findAll();
        List<LeaveDO> leaveDOList = leaveRepository.findByUser_Id(currentUserId);

        UserDO userDetails = userRepository.getById(currentUserId);

        Date hired_date = userDetails.getHire_date();
        Date current_date = new Date();

        Long monthsWorking = DateUtils.getMonthsBetween(hired_date, current_date);

        leaveType = leaveType.stream()
                .filter(leaveTypeDO -> DateUtils.isBetween(monthsWorking, leaveTypeDO.getMinServiceMonths(), leaveTypeDO.getMaxServiceMonths()))
                .collect(Collectors.toList());

        leaveType.forEach(leaveTypeDO -> {
            List<LeaveDO> leaveList = leaveDOList.stream()
                    .filter(leaveDO -> leaveDO.getLeaveType().getId().equals(leaveTypeDO.getId()))
                    .filter(leaveDO -> leaveDO.getStatus() == LeaveStatus.APPROVED)
                    .filter(leaveDO -> leaveDO.getStartDate().before(current_date)) // Use before() for Date, or isBefore() for LocalDate
                    .collect(Collectors.toList());

            AtomicReference<Long> totalUsed = new AtomicReference<>(Long.valueOf(0));
            leaveList.forEach(leaveDO -> {
                if (leaveDO.getEndDate().after(current_date)) {
                    totalUsed.set(totalUsed.get() + daysLeaveTaken(leaveDO.getStartDate(), current_date));

                } else {
                    totalUsed.set(totalUsed.get() + daysLeaveTaken(leaveDO.getStartDate(), leaveDO.getEndDate()));
                }
            });

            Integer balance = (int) (leaveTypeDO.getMaxDays() - totalUsed.get());
            response.add(new LeaveBalanceDTO(currentUserId, leaveTypeDO.getName(), balance));

        });

        return response;
    }

    public Long daysLeaveTaken(Date startDate, Date endDate){
        return Long.valueOf(0);
    }

    public List<LeaveCarryForwardDO> carryForwardLeave(Long currentUserId, Long userId, Integer year){
        return null;
    }
    public List<LeaveCarryForwardDO> getLeaveCarryForwardByYear(Long currentUserId, Long userId, Integer year){
        return null;
    }



}
