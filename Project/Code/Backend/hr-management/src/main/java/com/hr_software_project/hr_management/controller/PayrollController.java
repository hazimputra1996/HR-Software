package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.dto.*;
import com.hr_software_project.hr_management.entity.*;
import com.hr_software_project.hr_management.service.PayrollService;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private DataSource dataSource;



    @GetMapping("/getPayrollDetail")
    public SalaryStatementDTO getPayrollDetail(
            @RequestParam Long payrollId,
            @RequestParam Long currentUserId) {
        return payrollService.getPayrollDetail(currentUserId, payrollId);
    }

    @PostMapping("/createPayroll")
    public SalaryStatementDTO createPayroll(
            @RequestBody CreatePayrollRequestDTO req) {
        return payrollService.createPayroll(req);
    }

    @PutMapping("/updatePayroll")
    public SalaryStatementDTO updatePayroll(
            @RequestBody UpdatePayrollRequestDTO req) {
        return payrollService.updatePayroll(req);
    }

    @GetMapping("/getUserPayrolls")
    public List<SalaryStatementDTO> getUserPayrolls(
            @RequestParam Long currentUserId,
            @RequestParam Long userId) {
        return payrollService.getUserPayrolls(currentUserId, userId);
    }

    @PostMapping("/deduction/createDeduction")
    public DeductionDO createDeduction(
            @RequestBody CreateDeductionRequestDTO req) {
        return payrollService.createDeduction(req);
    }

    @GetMapping("/deduction/getDeductionDetail")
    public DeductionDO getDeductionDetail(
            @RequestParam Long currentUserId,
            @RequestParam Long deductionId) {
        return payrollService.getDeductionDetail(currentUserId, deductionId);
    }

    @GetMapping("/deduction/getAllDeductionDetail")
    public List<DeductionDO> getAllDeductionDetail(
            @RequestParam Long currentUserId) {
        return payrollService.getAllDeductionDetail(currentUserId);
    }

    @PutMapping("/deduction/updateDeduction")
    public DeductionDO updateDeduction(
            @RequestBody DeductionDO deduction,
            @RequestParam Long currentUserId) {
        return payrollService.updateDeduction(deduction, currentUserId);
    }

    @DeleteMapping("/deduction/deleteDeduction")
    public void deleteDeduction(
            @RequestParam Long currentUserId,
            @RequestParam Long deductionId) {
        payrollService.deleteDeduction(currentUserId, deductionId);
    }

    @PostMapping("/deduction/createDeductionForUser")
    public UserDeductionDO createDeductionForUser(
            @RequestBody CreateUserDeductionRequestDTO req) {
        return payrollService.createDeductionForUser(req);
    }

    @PutMapping("/deduction/updateDeductionForUser")
    public UserDeductionDO updateDeductionForUser(
            @RequestBody UserDeductionDO req,
            @RequestParam Long currentUserId) {
        return payrollService.updateDeductionForUser(req, currentUserId);
    }

    @DeleteMapping("/deduction/deleteDeductionForUser")
    public void deleteDeductionForUser(
            @RequestParam Long currentUserId,
            @RequestParam Long userDeductionId) {
        payrollService.deleteDeductionForUser(currentUserId, userDeductionId);
    }

    @PostMapping("/overtime/createOvertimeRecord")
    public OvertimeDO createOvertimeRecord(
            @RequestBody CreateOvertimeRequestDTO req) {
        return payrollService.createOvertimeRecord(req);
    }

    @GetMapping("/overtime/getOvertimeRecords")
    public List<OvertimeDO> getOvertimeRecords(
            @RequestParam Long currentUserId,
            @RequestParam Long userId) {
        return payrollService.getOvertimeRecords(currentUserId, userId);
    }

    @GetMapping("/overtime/getOvertimeRecordDetail")
    public OvertimeDO getOvertimeRecordDetail(
            @RequestParam Long currentUserId,
            @RequestParam Long overtimeId) {
        return payrollService.getOvertimeRecordDetail(currentUserId, overtimeId);
    }

    @PutMapping("/overtime/updateOvertimeRecord")
    public OvertimeDO updateOvertimeRecord(
            @RequestBody UpdateOvertimeRequestDTO req) {
        return payrollService.updateOvertimeRecord(req);
    }

    @DeleteMapping("/overtime/deleteOvertimeRecord")
    public void deleteOvertimeRecord(
            @RequestParam Long currentUserId,
            @RequestParam Long overtimeId) {
        payrollService.deleteOvertimeRecord(currentUserId, overtimeId);
    }

    @PostMapping("/public-holiday/createPublicHoliday")
    public PublicHolidayDO createPublicHoliday(
            @RequestBody CreatePublicHolidayRequestDTO req) {
        return payrollService.createPublicHoliday(req);
    }

    @GetMapping("/public-holiday/getPublicHolidays")
    public List<PublicHolidayDO> getPublicHolidays(
            @RequestParam Integer year) {
        return payrollService.getPublicHolidays(year);
    }

    @GetMapping("/public-holiday/getPublicHolidayDetail")
    public PublicHolidayDO getPublicHolidayDetail(
            @RequestParam Long publicHolidayId) {
        return payrollService.getPublicHolidayDetail(publicHolidayId);
    }

    @PutMapping("/public-holiday/updatePublicHoliday")
    public PublicHolidayDO updatePublicHoliday(
            @RequestBody PublicHolidayDO req,
            @RequestParam Long currentUserId) {
        return payrollService.updatePublicHoliday(req, currentUserId);
    }

    @DeleteMapping("/public-holiday/deletePublicHoliday")
    public void deletePublicHoliday(
            @RequestParam Long currentUserId,
            @RequestParam Long publicHolidayId) {
        payrollService.deletePublicHoliday(currentUserId, publicHolidayId);
    }

    @PostMapping("/allowance/createAllowance")
    public AllowanceDO createAllowance(
            @RequestBody CreateAllowanceRequestDTO req) {
        return payrollService.createAllowance(req);
    }

    @GetMapping("/allowance/getAllowanceDetail")
    public AllowanceDO getAllowanceDetail(
            @RequestParam Long currentUserId,
            @RequestParam Long allowanceId) {
        return payrollService.getAllowanceDetail(currentUserId, allowanceId);
    }

    @GetMapping("/allowance/getAllAllowanceDetailForUser")
    public List<AllowanceDO> getAllAllowanceDetailForUser(
            @RequestParam Long currentUserId,
            @RequestParam Long userId) {
        return payrollService.getAllAllowanceDetailForUser(currentUserId, userId);
    }

    @PutMapping("/allowance/updateAllowance")
    public AllowanceDO updateAllowance(
            @RequestBody AllowanceDO req,
            @RequestParam Long currentUserId) {
        return payrollService.updateAllowance(req, currentUserId);
    }

    @DeleteMapping("/allowance/deleteAllowance")
    public void deleteAllowance(
            @RequestParam Long currentUserId,
            @RequestParam Long allowanceId) {
        payrollService.deleteAllowance(currentUserId, allowanceId);
    }


    @GetMapping("/generate-pdf-db")
    public ResponseEntity<byte[]> generatePdfFromDb(
            @RequestParam Long currentUserId,
            @RequestParam Long payrollId,
            @RequestParam String reportName) {
        try {

            // Get PostgreSQL DB connection
            try (Connection connection = dataSource.getConnection()) {
                byte[] pdfData = payrollService.generateSalarySlip(currentUserId, payrollId, reportName, connection);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + reportName + ".pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfData);
            }
        } catch (RuntimeException | SQLException | JRException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
