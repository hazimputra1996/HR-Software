package com.hr_software_project.hr_management.controller;

import com.hr_software_project.hr_management.dto.*;
import com.hr_software_project.hr_management.entity.*;
import com.hr_software_project.hr_management.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;


    @GetMapping("/getPayrollDetail")
    public SalaryStatementDO getPayrollDetail(
            @RequestParam Long payrollId,
            @RequestParam Long currentUserId) {
        return payrollService.getPayrollDetail(currentUserId, payrollId);
    }

    @PostMapping("/createPayroll")
    public SalaryStatementDO createPayroll(
            @RequestBody CreatePayrollRequestDTO req) {
        return payrollService.createPayroll(req);
    }

    @PutMapping("/updatePayroll")
    public SalaryStatementDO updatePayroll(
            @RequestBody UpdatePayrollRequestDTO req) {
        return payrollService.updatePayroll(req);
    }

    @GetMapping("/getUserPayrolls")
    public List<SalaryStatementDO> getUserPayrolls(
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
            @RequestBody CreateDeductionRequestDTO req) {
        return payrollService.createDeductionForUser(req);
    }

    @PutMapping("/deduction/updateDeductionForUser")
    public UserDeductionDO updateDeductionForUser(
            @RequestBody UserDeductionDO req) {
        return payrollService.updateDeductionForUser(req);
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
            @RequestBody OvertimeDO req,
            @RequestParam Long currentUserId) {
        return payrollService.updateOvertimeRecord(req, currentUserId);
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
            @RequestParam Long currentUserId) {
        return payrollService.getPublicHolidays(currentUserId);
    }

    @GetMapping("/public-holiday/getPublicHolidayDetail")
    public PublicHolidayDO getPublicHolidayDetail(
            @RequestParam Long currentUserId,
            @RequestParam Long publicHolidayId) {
        return payrollService.getPublicHolidayDetail(currentUserId, publicHolidayId);
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



}
