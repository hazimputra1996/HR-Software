package com.hr_software_project.hr_management.service;

import com.hr_software_project.hr_management.dto.*;
import com.hr_software_project.hr_management.entity.*;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;


@Service
public interface PayrollService {


    SalaryStatementDTO getPayrollDetail(Long currentUserId, Long payrollId);
    SalaryStatementDTO createPayroll(CreatePayrollRequestDTO req);
    SalaryStatementDTO updatePayroll(UpdatePayrollRequestDTO req);
    List<SalaryStatementDTO> getUserPayrolls(Long currentUserId, Long userId);

    DeductionDO createDeduction(CreateDeductionRequestDTO req);
    DeductionDO getDeductionDetail(Long currentUserId, Long deductionId);
    List<DeductionDO> getAllDeductionDetail(Long currentUserId);
    DeductionDO updateDeduction(DeductionDO req, Long currentUserId);
    void deleteDeduction(Long currentUserId, Long deductionId);
    UserDeductionDO createDeductionForUser(CreateUserDeductionRequestDTO req);
    UserDeductionDO updateDeductionForUser(UserDeductionDO req , Long currentUserId);
    void deleteDeductionForUser(Long currentUserId, Long userDeductionId);
    OvertimeDO createOvertimeRecord(CreateOvertimeRequestDTO req);
    OvertimeDO updateOvertimeRecord(UpdateOvertimeRequestDTO req);
    void deleteOvertimeRecord(Long currentUserId, Long overtimeId);
    List<OvertimeDO> getOvertimeRecords(Long currentUserId, Long userId);
    OvertimeDO getOvertimeRecordDetail(Long currentUserId, Long overtimeId);
    PublicHolidayDO createPublicHoliday(CreatePublicHolidayRequestDTO req);
    List<PublicHolidayDO> getPublicHolidays(Integer year);
    PublicHolidayDO getPublicHolidayDetail(Long publicHolidayId);
    PublicHolidayDO updatePublicHoliday(PublicHolidayDO req, Long currentUserId);
    void deletePublicHoliday(Long currentUserId, Long publicHolidayId);
    AllowanceDO createAllowance(CreateAllowanceRequestDTO req);
    AllowanceDO getAllowanceDetail(Long currentUserId, Long allowanceId);
    AllowanceDO updateAllowance(AllowanceDO req, Long currentUserId);
    List<AllowanceDO> getAllAllowanceDetailForUser(Long currentUserId, Long userId);
    void deleteAllowance(Long currentUserId, Long allowanceId);
    public byte[] generateSalarySlip(Long currentUserId, Long payrollId, String reportType, Connection connection)  throws JRException;



}
