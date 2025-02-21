package com.hr_software_project.hr_management.method;

import com.hr_software_project.hr_management.dto.*;
import com.hr_software_project.hr_management.entity.*;
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

    public SalaryStatementDO getPayrollDetail(Long currentUserId, String payrollId){
        return null;
    }

    public SalaryStatementDO createPayroll(CreatePayrollRequestDTO req){
        return null;
    }

    public UserDO updatePayroll(UpdatePayrollRequestDTO req){
        return null;
    }

    public List<SalaryStatementDO> getUserPayrolls(Long currentUserId, Long userId){
        return null;
    }

    public SalaryStatementDO createDeduction(CreateDeductionRequestDTO req){
        return null;
    }

    public List<DeductionDO> getDeductions(Long currentUserId, Long userId){
        return null;
    }

    public DeductionDO getDeductionDetail(Long currentUserId, Long deductionId){
        return null;
    }

    public List<DeductionDO> getAllDeductionDetail(Long currentUserId){
        return null;
    }

    public DeductionDO updateDeduction(DeductionDO req){
        return null;
    }

    public void deleteDeduction(Long currentUserId, Long deductionId){
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
