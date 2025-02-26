package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.constant.Constants;
import com.hr_software_project.hr_management.dto.CreateLeaveRequestDTO;
import com.hr_software_project.hr_management.dto.LeaveBalanceDTO;
import com.hr_software_project.hr_management.entity.LeaveCarryForwardDO;
import com.hr_software_project.hr_management.entity.LeaveDO;
import com.hr_software_project.hr_management.entity.LeaveTypeDO;
import com.hr_software_project.hr_management.entity.PublicHolidayDO;
import com.hr_software_project.hr_management.entity.UserDO;
import com.hr_software_project.hr_management.enums.LeaveStatus;
import com.hr_software_project.hr_management.enums.Role;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;
import com.hr_software_project.hr_management.repository.*;
import com.hr_software_project.hr_management.service.LeaveService;
import com.hr_software_project.hr_management.utils.DateUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;
    @Autowired
    private PublicHolidayRepository publicHolidayRepository;
    @Autowired
    private LeaveCarryForwardRepository leaveCarryForwardRepository;

    public LeaveDO getLeaveDetail(Long currentUserId, Long leaveId){

        LeaveDO leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.LEAVE_NOT_FOUND));

        UserDO leaveUser = leave.getUser();
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

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
        UserDO userDO = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

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

        LeaveDO leave = leaveRepository.findById(req.getId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.LEAVE_NOT_FOUND));

        UserDO leaveUser = leave.getUser();
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser == null || !currentUser.getId().equals(leaveUser.getSupervisor().getId())){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        } else {
            return leaveRepository.save(leave);
        }
    }

    public List<LeaveDO> getAllLeaveByUser(Long currentUserId){
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser == null || !currentUser.getRole().equals(Role.ADMIN)){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        } else {
            return leaveRepository.findAll();
        }
    }

    public List<LeaveDO> getAllLeaveFilterByStatus(Long currentUserId, String status, Boolean allUser){
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

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

        UserDO userDetails = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

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

        List<PublicHolidayDO> allPublicHoliday = new ArrayList<>(publicHolidayRepository.findByDateBetween(startDate, endDate));

        // Find the days between startDate and endDate excluding weekends and public holidays
        return DateUtils.getWorkingDaysBetween(startDate, endDate, allPublicHoliday);
    }

    public List<LeaveCarryForwardDO> carryForwardLeave(Long currentUserId, Long userId, Integer year){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser == null || !currentUser.getRole().equals(Role.ADMIN) || !currentUser.getId().equals(user.getSupervisor().getId())){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

        List<LeaveCarryForwardDO> leaveCarryForwardDOList = new ArrayList<>();

        List<LeaveDO> leaveDOList = leaveRepository.findByUser_Id_And_Between_Date(userId, new Date(year, 1, 1), new Date(year, 12, 31));
        List<LeaveTypeDO> leaveType = leaveTypeRepository.findByName(Constants.ANNUAL_LEAVE);
        List<LeaveCarryForwardDO> leaveCarryForwardDOListByYear = new ArrayList<>(getLeaveCarryForwardByYear(currentUserId, userId, year - 1));

        // total carry forward from leaveCarryForwardDOListByYear
        Integer totalCarryForwardLastYear = leaveCarryForwardDOListByYear.stream()
                .map(LeaveCarryForwardDO::getCarriedDays)
                .reduce(0, Integer::sum);



        leaveType.forEach(leaveTypeDO -> {
            List<LeaveDO> leaveList = leaveDOList.stream()
                    .filter(leaveDO -> leaveDO.getLeaveType().getId().equals(leaveTypeDO.getId()))
                    .collect(Collectors.toList());

            AtomicReference<Long> totalUsed = new AtomicReference<>(Long.valueOf(0));
            leaveList.forEach(leaveDO -> {
                Date startDate = leaveDO.getStartDate();
                Date endDate = leaveDO.getEndDate();

                if (leaveDO.getEndDate().after(new Date(year, 12, 31))) {
                    endDate = new Date(year, 12, 31);

                } else if (leaveDO.getStartDate().before(new Date(year, 1, 1))){
                    startDate = new Date(year, 1, 1);

                }

                totalUsed.set(totalUsed.get() + daysLeaveTaken(startDate, endDate));
            });

            Integer balance = (int) (leaveTypeDO.getMaxDays() + totalCarryForwardLastYear - totalUsed.get());

            LeaveCarryForwardDO leaveCarryForward = new LeaveCarryForwardDO();
            leaveCarryForward.setCarriedDays(balance);
            leaveCarryForward.setUser(user);
            leaveCarryForward.setPreviousYear(year);
            leaveCarryForward.setExpiryDate(new Date(year + 1, 12, 31));


            leaveCarryForwardDOList.add(leaveCarryForward);

        });

        return leaveCarryForwardRepository.saveAll(leaveCarryForwardDOList);
    }
    public List<LeaveCarryForwardDO> getLeaveCarryForwardByYear(Long currentUserId, Long userId, Integer year){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser == null || !currentUser.getRole().equals(Role.ADMIN) || !currentUser.getId().equals(user.getSupervisor().getId()) || !currentUser.getId().equals(user.getId())){
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

        return leaveCarryForwardRepository.findByUser_Id_And_PreviousYear(userId, year);
    }



}
