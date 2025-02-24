package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.*;
import com.hr_software_project.hr_management.entity.*;
import com.hr_software_project.hr_management.enums.Role;
import com.hr_software_project.hr_management.error.ServiceErrorCodes;
import com.hr_software_project.hr_management.error.ServiceException;
import com.hr_software_project.hr_management.repository.DeductionRepository;
import com.hr_software_project.hr_management.repository.SalaryStatementRepository;
import com.hr_software_project.hr_management.repository.UserRepository;
import com.hr_software_project.hr_management.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SalaryStatementRepository salaryStatementRepository;
    @Autowired
    private DeductionRepository deductionRepository;

    public SalaryStatementDO getPayrollDetail(Long currentUserId, Long payrollId){

        UserDO currentUser = userRepository.getById(currentUserId);
        SalaryStatementDO payroll = salaryStatementRepository.getById(payrollId);

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(payroll.getUser().getId())){
            return payroll;
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

    }

    public SalaryStatementDO createPayroll(CreatePayrollRequestDTO req){
        return null;
    }

    public SalaryStatementDO updatePayroll(UpdatePayrollRequestDTO req){

        UserDO currentUser = userRepository.getById(req.getCurrentUserId());

        if (currentUser.getRole().equals(Role.ADMIN)){
            SalaryStatementDO payroll = salaryStatementRepository.getById(req.getId());

            payroll.setBaseSalary(req.getBaseSalary());
            payroll.setBonuses(req.getBonuses());
            payroll.setAllowances(req.getAllowances());
            payroll.setOvertimePay(req.getOvertimePay());
            payroll.setGrossSalary(req.getGrossSalary());
            payroll.setNetSalary(req.getNetSalary());
            payroll.setTotalDeductions(req.getTotalDeductions());
            payroll.setRemarks(req.getRemarks());
            return salaryStatementRepository.save(payroll);

        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public List<SalaryStatementDO> getUserPayrolls(Long currentUserId, Long userId){
        UserDO currentUser = userRepository.getById(currentUserId);
        UserDO user = userRepository.getById(userId);

        if (currentUser.getRole().equals(Role.ADMIN) || currentUser.getId().equals(user.getId())){
            return salaryStatementRepository.findByUser_Id(userId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public DeductionDO createDeduction(CreateDeductionRequestDTO req){
        UserDO currentUser = userRepository.getById(req.getCurrentUserId());

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
        UserDO currentUser = userRepository.getById(currentUserId);
        DeductionDO deduction = deductionRepository.getById(deductionId);

        if (currentUser.getRole().equals(Role.ADMIN)){
            return deduction;
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }

    }

    public List<DeductionDO> getAllDeductionDetail(Long currentUserId){

        UserDO currentUser = userRepository.getById(currentUserId);

        if (currentUser.getRole().equals(Role.ADMIN)){
            return deductionRepository.findAll();
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public DeductionDO updateDeduction(DeductionDO req, Long currentUserId){

        UserDO currentUser = userRepository.getById(currentUserId);

        if (currentUser.getRole().equals(Role.ADMIN)){
            DeductionDO deduction = deductionRepository.getById(req.getId());

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

        UserDO currentUser = userRepository.getById(currentUserId);

        if (currentUser.getRole().equals(Role.ADMIN)){
            deductionRepository.deleteById(deductionId);
        } else {
            throw new ServiceException(ServiceErrorCodes.UNAUTHORIZED);
        }
    }

    public UserDeductionDO createDeductionForUser(CreateDeductionRequestDTO req){
        return null;
    }

    public UserDeductionDO updateDeductionForUser(UserDeductionDO req){
        return null;
    }

    public void deleteDeductionForUser(Long currentUserId, Long userDeductionId){
    }

    public OvertimeDO createOvertimeRecord(CreateOvertimeRequestDTO req){
        return null;
    }

    public OvertimeDO updateOvertimeRecord(OvertimeDO req, Long currentUserId){
        return null;
    }

    public void deleteOvertimeRecord(Long currentUserId, Long overtimeId){
    }

    public List<OvertimeDO> getOvertimeRecords(Long currentUserId, Long userId){
        return null;
    }

    public OvertimeDO getOvertimeRecordDetail(Long currentUserId, Long overtimeId){
        return null;
    }

    public PublicHolidayDO createPublicHoliday(CreatePublicHolidayRequestDTO req){
        return null;
    }

    public List<PublicHolidayDO> getPublicHolidays(Long currentUserId){
        return null;
    }

    public PublicHolidayDO getPublicHolidayDetail(Long currentUserId, Long publicHolidayId){
        return null;
    }

    public PublicHolidayDO updatePublicHoliday(PublicHolidayDO req, Long currentUserId){
        return null;
    }

    public void deletePublicHoliday(Long currentUserId, Long publicHolidayId){
    }

}
