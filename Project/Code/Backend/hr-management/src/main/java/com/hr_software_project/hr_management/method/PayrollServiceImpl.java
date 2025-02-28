package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.*;
import com.hr_software_project.hr_management.entity.*;
import com.hr_software_project.hr_management.enums.DeductionType;
import com.hr_software_project.hr_management.enums.Role;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;
import com.hr_software_project.hr_management.repository.*;
import com.hr_software_project.hr_management.service.PayrollService;
import com.hr_software_project.hr_management.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SalaryStatementRepository salaryStatementRepository;
    @Autowired
    private DeductionRepository deductionRepository;
    @Autowired
    private UserDeductionRepository userDeductionRepository;
    @Autowired
    private OvertimeRepository overtimeRepository;
    @Autowired
    private PublicHolidayRepository publicHolidayRepository;
    @Autowired
    private AllowanceRepository allowanceRepository;
    @Autowired
    private SalaryStatementOvertimeRepository salaryStatementOvertimeRepository;
    @Autowired
    private SalaryStatementAllowanceRepository salaryStatementAllowanceRepository;
    @Autowired
    private SalaryStatementEmployeeDeductionRepository salaryStatementEmployeeDeductionRepository;
    @Autowired
    private SalaryStatementEmployerDeductionRepository salaryStatementEmployerDeductionRepository;
    @Autowired
    private EmployerDeductionRepository employerDeductionRepository;
    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    public SalaryStatementDTO getPayrollDetail(Long currentUserId, Long payrollId) {
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        SalaryStatementDO payroll = salaryStatementRepository.findById(payrollId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(payroll.getUser().getId())) {

            SalaryStatementDTO result = new SalaryStatementDTO();

            CompanyDO companyDetails = companyServiceImpl.getCompanyDetails();

            List<SalaryStatementEmployerDeductionDO> employerDeductionsDOs = salaryStatementEmployerDeductionRepository.findByUser_Id(payroll.getUser().getId());
            List<SalaryStatementAllowanceDO> allowancesDO = salaryStatementAllowanceRepository.findByUser_Id(payroll.getUser().getId());
            List<SalaryStatementEmployeeDeductionDO> employeeDeductionDOS = salaryStatementEmployeeDeductionRepository.findByUser_Id(payroll.getUser().getId());
            List<SalaryStatementOvertimeDO> overtimesDOs = salaryStatementOvertimeRepository.findByUser_Id(payroll.getUser().getId());

            Double totalAllowances = allowancesDO.stream().mapToDouble(SalaryStatementAllowanceDO::getAmount).sum();
            Double totalEmployeeDeductions = employeeDeductionDOS.stream().mapToDouble(SalaryStatementEmployeeDeductionDO::getAmount).sum();
            Double totalEmployerDeductions = employerDeductionsDOs.stream().mapToDouble(SalaryStatementEmployerDeductionDO::getAmount).sum();
            Double totalOvertimes = overtimesDOs.stream().mapToDouble(SalaryStatementOvertimeDO::getAmount).sum();

            Double grossSalary = payroll.getBase_salary() + totalAllowances + totalOvertimes;
            Double totalDeductions = totalEmployeeDeductions;
            Double netSalary = grossSalary - totalDeductions;

            result.setPayrollId(payroll.getId().toString());
            result.setCompanyName(companyDetails.getCompanyName());
            result.setCompanyRegistrationNumber(companyDetails.getCompanyRegistrationNumber());
            result.setCompanyNumber(companyDetails.getCompanyNumber());
            result.setEmployeeName(payroll.getUser().getFirst_name() + " " + payroll.getUser().getLast_name());
            result.setEmployeeRegistrationNumber(payroll.getUser().getRegistrationNumber());
            result.setEmployeeNumber(payroll.getUser().getEmployeeNumber());
            result.setEpfNumber(payroll.getUser().getEpf_number());
            result.setDateFrom(payroll.getStartDate());
            result.setDateTo(payroll.getEndDate());
            result.setStatementDate(payroll.getStatementDate());
            result.setIncomeTaxNumber(payroll.getUser().getIncome_tax_number());

            result.setEmployerDeductions(employerDeductionsDOs.stream().map(employerDeductionDO -> {
                SalaryStatementDTO.EmployerDeductionDTO employerDeductionDTO = new SalaryStatementDTO.EmployerDeductionDTO();
                employerDeductionDTO.setName(employerDeductionDO.getName());
                employerDeductionDTO.setDescription(employerDeductionDO.getDeduction().getDescription());
                employerDeductionDTO.setAmount(employerDeductionDO.getAmount());
                return employerDeductionDTO;
            }).collect(Collectors.toList()));

            result.setEmployeeDeductions(employeeDeductionDOS.stream().map(employeeDeductionDO -> {
                SalaryStatementDTO.EmployeeDeductionDTO employeeDeductionDTO = new SalaryStatementDTO.EmployeeDeductionDTO();
                employeeDeductionDTO.setName(employeeDeductionDO.getName());
                employeeDeductionDTO.setDescription(employeeDeductionDO.getDeduction().getDescription());
                employeeDeductionDTO.setAmount(employeeDeductionDO.getAmount());
                return employeeDeductionDTO;

            }).collect(Collectors.toList()));

            result.setAllowances(allowancesDO.stream().map(allowanceDO -> {
                SalaryStatementDTO.AllowanceDTO allowanceDTO = new SalaryStatementDTO.AllowanceDTO();
                allowanceDTO.setName(allowanceDO.getName());
                allowanceDTO.setDescription(allowanceDO.getAllowance().getRemarks());
                allowanceDTO.setAmount(allowanceDO.getAmount());
                return allowanceDTO;
            }).collect(Collectors.toList()));

            Double totalOvertimeHours = overtimesDOs.stream().mapToDouble(overtimeDO -> overtimeDO.getOvertime().getHoursWorked()).sum();
            Double totalOvertimeAmount = overtimesDOs.stream().mapToDouble(SalaryStatementOvertimeDO::getAmount).sum();
            String overtimeDescription = "Total Overtime Hours: " + totalOvertimeHours;

            result.setOvertimes(new SalaryStatementDTO.OvertimeDTO("Overtime",overtimeDescription, totalOvertimeAmount));
            result.setBaseSalary(payroll.getBase_salary());
            result.setGrossSalary(grossSalary);
            result.setTotalDeductions(totalDeductions);
            result.setNetSalary(netSalary);
            result.setRemarks(payroll.getRemarks());

            return result;
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }



    public SalaryStatementDTO createPayroll(CreatePayrollRequestDTO req){
        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));


        if (currentUser.getRole().equals(Role.ADMIN)){
            UserDO user = userRepository.findById(req.getUserId())
                    .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

            CompanyDO companyDetails = companyServiceImpl.getCompanyDetails();
            Integer cuttOffDay = companyDetails.getCutOffDate();

            Double baseSalary = user.getSalary();
            SalaryStatementDO payroll = new SalaryStatementDO();
            payroll.setUser(user);
            payroll.setRemarks(req.getRemarks());
            payroll.setStatementDate(req.getDate());

            Date startDate = DateUtils.getStartDateBasedCutOff(req.getDate(), cuttOffDay);
            Date endDate = DateUtils.getEndDateBasedCutOff(req.getDate(), cuttOffDay);
            Date userHiredDate = user.getHire_date();
            Date userStartDate = startDate;

            if (startDate.after(userHiredDate)) {
                startDate = user.getHire_date();

                Integer numberOfWorkingDaysPerWeek = user.getNumber_of_working_days_per_week();
                Long workingDays = DateUtils.getWorkingDaysBetween(startDate, endDate, new ArrayList<>(), true, true, numberOfWorkingDaysPerWeek);
                Double dailySalary = baseSalary / 30;
                Double monthlySalary = dailySalary * workingDays;
                baseSalary = monthlySalary;
            }

            payroll.setStartDate(startDate);
            payroll.setEndDate(endDate);
            payroll.setBase_salary(baseSalary);
            payroll = salaryStatementRepository.save(payroll);

            List<AllowanceDO> allowances = allowanceRepository.findByUser_Id(req.getUserId());
            List<UserDeductionDO> employeeDeductions = userDeductionRepository.findByUser_Id(req.getUserId());
            List<EmployerDeductionDO> employerDeductions = employerDeductionRepository.findByUser_Id(req.getUserId());
            List<OvertimeDO> overtime = overtimeRepository.findByUser_Id(req.getUserId());

            // filter allowances, employeeDeductions and overtime based on date
            allowances = allowances.stream()
                    .filter(a -> req.getDate().after(a.getDateStarted()) && req.getDate().before(a.getDateEnded()))
                    .collect(Collectors.toList());

            employeeDeductions = employeeDeductions.stream()
                    .filter(a -> req.getDate().after(a.getStartedDate()) && req.getDate().before(a.getEndedDate()))
                    .collect(Collectors.toList());

            employerDeductions = employerDeductions.stream()
                    .filter(a -> req.getDate().after(a.getStartedDate()) && req.getDate().before(a.getEndedDate()))
                    .collect(Collectors.toList());

            overtime = overtime.stream()
                    .filter(a -> {
                        Calendar reqCal = Calendar.getInstance();
                        reqCal.setTime(req.getDate());

                        Calendar otCal = Calendar.getInstance();
                        otCal.setTime(a.getOvertimeDate());

                        return reqCal.get(Calendar.MONTH) == otCal.get(Calendar.MONTH) &&
                                reqCal.get(Calendar.YEAR) == otCal.get(Calendar.YEAR);
                    })
                    .collect(Collectors.toList());

            List<SalaryStatementAllowanceDO> salaryStatementAllowances = new ArrayList<>();
            List<SalaryStatementEmployeeDeductionDO> salaryStatementEmployeeDeductions = new ArrayList<>();
            List<SalaryStatementEmployerDeductionDO> salaryStatementEmployerDeductions = new ArrayList<>();
            List<SalaryStatementOvertimeDO> salaryStatementOvertimes = new ArrayList<>();

            SalaryStatementDO finalPayroll = payroll;
            Double finalBaseSalary = baseSalary;
            allowances.forEach(allowance -> {
                SalaryStatementAllowanceDO salaryStatementAllowance = new SalaryStatementAllowanceDO();

                switch (allowance.getAllowanceType()) {
                    case DeductionType.FIXED -> salaryStatementAllowance.setAmount(allowance.getAmount());
                    case DeductionType.PERCENTAGE -> salaryStatementAllowance.setAmount(allowance.getAmount() * finalBaseSalary);
                }

                salaryStatementAllowance.setUser(user);
                salaryStatementAllowance.setName(allowance.getName());
                salaryStatementAllowance.setAllowance(allowance);
                salaryStatementAllowance.setSalaryStatement(finalPayroll);

                salaryStatementAllowances.add(salaryStatementAllowance);


            });

            Double finalBaseSalary1 = baseSalary;
            employeeDeductions.forEach(employeeDeduction -> {
                SalaryStatementEmployeeDeductionDO salaryStatementEmployeeDeduction = new SalaryStatementEmployeeDeductionDO();

                switch (employeeDeduction.getDeduction().getDeductionType()){
                    case DeductionType.FIXED -> salaryStatementEmployeeDeduction.setAmount(employeeDeduction.getAmount());
                    case DeductionType.PERCENTAGE -> salaryStatementEmployeeDeduction.setAmount(employeeDeduction.getAmount() * finalBaseSalary1);
                }

                salaryStatementEmployeeDeduction.setUser(user);
                salaryStatementEmployeeDeduction.setDeduction(employeeDeduction.getDeduction());
                salaryStatementEmployeeDeduction.setSalaryStatement(finalPayroll);
                salaryStatementEmployeeDeduction.setName(salaryStatementEmployeeDeduction.getDeduction().getName());

                salaryStatementEmployeeDeductions.add(salaryStatementEmployeeDeduction);

            });
            Double finalBaseSalary2 = baseSalary;
            employerDeductions.forEach(employerDeduction -> {
                SalaryStatementEmployerDeductionDO salaryStatementEmployerDeduction = new SalaryStatementEmployerDeductionDO();

                switch (employerDeduction.getDeduction().getDeductionType()){
                    case DeductionType.FIXED -> salaryStatementEmployerDeduction.setAmount(employerDeduction.getAmount());
                    case DeductionType.PERCENTAGE -> salaryStatementEmployerDeduction.setAmount(employerDeduction.getAmount() * finalBaseSalary2);
                }

                salaryStatementEmployerDeduction.setUser(user);
                salaryStatementEmployerDeduction.setDeduction(employerDeduction.getDeduction());
                salaryStatementEmployerDeduction.setSalaryStatement(finalPayroll);
                salaryStatementEmployerDeduction.setName(salaryStatementEmployerDeduction.getDeduction().getName());

                salaryStatementEmployerDeductions.add(salaryStatementEmployerDeduction);


            });

            overtime.forEach(overtimeDO -> {
                SalaryStatementOvertimeDO salaryStatementOvertime = new SalaryStatementOvertimeDO();

                salaryStatementOvertime.setUser(user);
                salaryStatementOvertime.setAmount(overtimeDO.getTotalOvertimePrice());
                salaryStatementOvertime.setOvertime(overtimeDO);
                salaryStatementOvertime.setSalaryStatement(finalPayroll);
                salaryStatementOvertime.setRemarks(overtimeDO.getRemarks());

                salaryStatementOvertimes.add(salaryStatementOvertime);

            });

            salaryStatementAllowanceRepository.saveAll(salaryStatementAllowances);
            salaryStatementEmployeeDeductionRepository.saveAll(salaryStatementEmployeeDeductions);
            salaryStatementEmployerDeductionRepository.saveAll(salaryStatementEmployerDeductions);
            salaryStatementOvertimeRepository.saveAll(salaryStatementOvertimes);

            return getPayrollDetail(req.getCurrentUserId(), finalPayroll.getId());

        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public SalaryStatementDTO updatePayroll(UpdatePayrollRequestDTO req) {
    UserDO currentUser = userRepository.findById(req.getCurrentUserId())
        .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

    if (currentUser.getRole().equals(Role.ADMIN)) {
        SalaryStatementDO payroll = salaryStatementRepository.findById(req.getId())
            .orElseThrow(() -> new ServiceException(ServiceErrorCodes.PAYROLL_NOT_FOUND));

        payroll.setRemarks(req.getRemarks()); // can edit remarks only
        salaryStatementRepository.save(payroll);

        return getPayrollDetail(req.getCurrentUserId(), payroll.getId());
    } else {
        throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
    }
}

    public List<SalaryStatementDTO> getUserPayrolls(Long currentUserId, Long userId) {

    List<SalaryStatementDTO> finalPayrolls = new ArrayList<>();

    List<SalaryStatementDO> payrolls = salaryStatementRepository.findByUser_Id(userId);

    payrolls.forEach(finalPayroll -> {
        finalPayrolls.add(getPayrollDetail(currentUserId, finalPayroll.getId()));
    });

    return finalPayrolls;

}

    public DeductionDO createDeduction(CreateDeductionRequestDTO req){
        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            DeductionDO deduction = new DeductionDO();

            deduction.setName(req.getName());
            deduction.setRate(req.getRate());
            deduction.setDescription(req.getDescription());
            deduction.setDeductionType(req.getDeductionType());
            deduction.setMaxLimit(req.getMaxLimit());

            return deductionRepository.save(deduction);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public DeductionDO getDeductionDetail(Long currentUserId, Long deductionId){
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        DeductionDO deduction = deductionRepository.findById(deductionId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.DEDUCTION_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            return deduction;
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

    }

    public List<DeductionDO> getAllDeductionDetail(Long currentUserId){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            return deductionRepository.findAll();
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public DeductionDO updateDeduction(DeductionDO req, Long currentUserId){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            DeductionDO deduction = deductionRepository.findById(req.getId())
                    .orElseThrow(() -> new ServiceException(ServiceErrorCodes.DEDUCTION_NOT_FOUND));

            deduction.setName(req.getName());
            deduction.setRate(req.getRate());
            deduction.setDescription(req.getDescription());
            deduction.setDeductionType(req.getDeductionType());
            deduction.setMaxLimit(req.getMaxLimit());

            return deductionRepository.save(deduction);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public void deleteDeduction(Long currentUserId, Long deductionId){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            deductionRepository.deleteById(deductionId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public UserDeductionDO createDeductionForUser(CreateUserDeductionRequestDTO req){
        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)) {
            UserDO user = userRepository.findById(req.getUserId())
                    .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
            DeductionDO deduction = deductionRepository.findById(req.getDeductionId())
                    .orElseThrow(() -> new ServiceException(ServiceErrorCodes.DEDUCTION_NOT_FOUND));

            UserDeductionDO userDeduction = new UserDeductionDO();
            userDeduction.setUser(user);
            userDeduction.setDeduction(deduction);
            userDeduction.setAmount(req.getAmount());
            userDeduction.setStartedDate(req.getStartedDate());
            userDeduction.setEndedDate(req.getEndedDate());

            return userDeductionRepository.save(userDeduction);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public UserDeductionDO updateDeductionForUser(UserDeductionDO req, Long currentUserId){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){

            return userDeductionRepository.save(req);

        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public void deleteDeductionForUser(Long currentUserId, Long userDeductionId){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)) {
            userDeductionRepository.deleteById(userDeductionId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public OvertimeDO createOvertimeRecord(CreateOvertimeRequestDTO req){

        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        UserDO user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(user.getId()) || currentUser.getId().equals(user.getSupervisor().getId())) {
            OvertimeDO overtime = new OvertimeDO();

            Double salary = user.getSalary();
            Double dailyWorkingHours = user.getDaily_working_hours();
            Integer numberOfWorkingDaysPerWeek = user.getNumber_of_working_days_per_week();

            Double hourlyRate = salary / (dailyWorkingHours * numberOfWorkingDaysPerWeek * 4.33); // 4.33 is the average number of weeks in a month
            Double overtimeRate = hourlyRate * req.getRate();

            Double overtimeHours = DateUtils.getHoursBetween(req.getStartTime(), req.getEndTime());

            overtime.setUser(user);
            overtime.setOvertimeDate(req.getOvertimeDate());
            overtime.setStartTime(req.getStartTime());
            overtime.setEndTime(req.getEndTime());
            overtime.setOvertimeRate(overtimeRate);
            overtime.setHoursWorked(overtimeHours);
            overtime.setTotalOvertimePrice(overtimeRate * overtimeHours);
            overtime.setRemarks(req.getRemarks());

            return overtimeRepository.save(overtime);

        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public OvertimeDO updateOvertimeRecord(UpdateOvertimeRequestDTO req){

        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        UserDO user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(user.getId()) || currentUser.getId().equals(user.getSupervisor().getId())) {

            OvertimeDO overtime = new OvertimeDO();

            Double salary = user.getSalary();
            Double dailyWorkingHours = user.getDaily_working_hours();
            Integer numberOfWorkingDaysPerWeek = user.getNumber_of_working_days_per_week();

            Double hourlyRate = salary / (dailyWorkingHours * numberOfWorkingDaysPerWeek * 4.33); // 4.33 is the average number of weeks in a month
            Double overtimeRate = hourlyRate * req.getRate();

            Double overtimeHours = DateUtils.getHoursBetween(req.getStartTime(), req.getEndTime());

            overtime.setUser(user);
            overtime.setId(req.getOvertimeId());
            overtime.setOvertimeDate(req.getOvertimeDate());
            overtime.setStartTime(req.getStartTime());
            overtime.setEndTime(req.getEndTime());
            overtime.setOvertimeRate(overtimeRate);
            overtime.setHoursWorked(overtimeHours);
            overtime.setTotalOvertimePrice(overtimeRate * overtimeHours);
            overtime.setRemarks(req.getRemarks());

            return overtimeRepository.save(overtime);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public void deleteOvertimeRecord(Long currentUserId, Long overtimeId) {

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        OvertimeDO overtime = overtimeRepository.findById(overtimeId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.OVERTIME_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(overtime.getUser().getId()) || currentUser.getId().equals(overtime.getUser().getSupervisor().getId())) {
            overtimeRepository.deleteById(overtimeId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public List<OvertimeDO> getOvertimeRecords(Long currentUserId, Long userId) {

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(user.getId()) || currentUser.getId().equals(user.getSupervisor().getId())) {
            return overtimeRepository.findByUser_Id(userId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public OvertimeDO getOvertimeRecordDetail(Long currentUserId, Long overtimeId){

        OvertimeDO overtime = overtimeRepository.findById(overtimeId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.OVERTIME_NOT_FOUND));
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        UserDO user = overtime.getUser();

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(user.getId()) || currentUser.getId().equals(user.getSupervisor().getId())) {
            return overtime;
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public PublicHolidayDO createPublicHoliday(CreatePublicHolidayRequestDTO req){

        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            PublicHolidayDO publicHoliday = new PublicHolidayDO();

            publicHoliday.setName(req.getName());
            publicHoliday.setDate(req.getDate());
            publicHoliday.setCountry(req.getCountry());
            publicHoliday.setState(req.getState());
            publicHoliday.setRecurring(false);

            return publicHolidayRepository.save(publicHoliday);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public List<PublicHolidayDO> getPublicHolidays(Integer year){

        return publicHolidayRepository.findByYear(year);
    }

    public PublicHolidayDO getPublicHolidayDetail(Long publicHolidayId){

        return publicHolidayRepository.findById(publicHolidayId).orElse(null);
    }

    public PublicHolidayDO updatePublicHoliday(PublicHolidayDO req, Long currentUserId){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            PublicHolidayDO publicHoliday = publicHolidayRepository.findById(req.getId())
                            .orElseThrow(() -> new ServiceException(ServiceErrorCodes.PUBLIC_HOLIDAY_NOT_FOUND));

            publicHoliday.setName(req.getName());
            publicHoliday.setDate(req.getDate());
            publicHoliday.setCountry(req.getCountry());
            publicHoliday.setState(req.getState());
            publicHoliday.setRecurring(false);

            return publicHolidayRepository.save(publicHoliday);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

    }

    public void deletePublicHoliday(Long currentUserId, Long publicHolidayId){

        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

            if (currentUser.getRole().equals(Role.ADMIN)){
                publicHolidayRepository.deleteById(publicHolidayId);
            } else {
                throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
            }
    }

    public AllowanceDO createAllowance(CreateAllowanceRequestDTO req){
        UserDO currentUser = userRepository.findById(req.getCurrentUserId())
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            AllowanceDO allowance = new AllowanceDO();

            allowance.setName(req.getName());
            allowance.setOneTimeBonus(req.getOneTimeBonus());
            allowance.setAmount(req.getAmount());
            allowance.setDateStarted(req.getDateStarted());
            allowance.setDateEnded(req.getDateEnded());
            allowance.setRemarks(req.getRemarks());
            allowance.setAllowanceType(req.getAllowanceType());

            return allowanceRepository.save(allowance);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public AllowanceDO getAllowanceDetail(Long currentUserId, Long allowanceId){
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        AllowanceDO allowance = allowanceRepository.findById(allowanceId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.ALLOWANCE_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            return allowance;
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public AllowanceDO updateAllowance(AllowanceDO req, Long currentUserId){
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            AllowanceDO allowance = allowanceRepository.findById(req.getId())
                    .orElseThrow(() -> new ServiceException(ServiceErrorCodes.ALLOWANCE_NOT_FOUND));

            allowance.setName(req.getName());
            allowance.setOneTimeBonus(req.getOneTimeBonus());
            allowance.setAmount(req.getAmount());
            allowance.setDateStarted(req.getDateStarted());
            allowance.setDateEnded(req.getDateEnded());
            allowance.setRemarks(req.getRemarks());
            allowance.setAllowanceType(req.getAllowanceType());

            return allowanceRepository.save(allowance);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public List<AllowanceDO> getAllAllowanceDetailForUser(Long currentUserId, Long userId){
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));
        UserDO user = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(user.getId())) {
            return allowanceRepository.findByUser_Id(userId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public void deleteAllowance(Long currentUserId, Long allowanceId){
        UserDO currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ServiceException(ServiceErrorCodes.USER_NOT_FOUND));

        if (currentUser.getRole().equals(Role.ADMIN)){
            allowanceRepository.deleteById(allowanceId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

}
