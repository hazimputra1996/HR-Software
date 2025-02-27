package com.hr_software_project.hr_management.dto;

import com.hr_software_project.hr_management.entity.UserDO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryStatementDTO {


    private String payrollId;
    private String companyName;
    private String companyRegistrationNumber;
    private String companyNumber;
    private String employeeName;
    private String employeeRegistrationNumber;
    private String employeeNumber;
    private String epfNumber;
    private Date dateFrom;
    private Date dateTo;
    private Date statementDate;
    private String incomeTaxNumber;
    private List<EmployerDeductionDTO> employerDeductions;
    private List<EmployeeDeductionDTO> employeeDeductions;
    private List<AllowanceDTO> allowances;
    private List<OvertimeDTO> overtimes;
    private Double baseSalary;
    private Double grossSalary;
    private double totalDeductions;
    private double netSalary;


    private String remarks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployerDeductionDTO {
        private String name;
        private String description;
        private String amount;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmployeeDeductionDTO {
        private String name;
        private String description;
        private String amount;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AllowanceDTO {
        private String name;
        private String description;
        private String amount;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OvertimeDTO {
        private String name;
        private String description;
        private String amount;

    }
}

