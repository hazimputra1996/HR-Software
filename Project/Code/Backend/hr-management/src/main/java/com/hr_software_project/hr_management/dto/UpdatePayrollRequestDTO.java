package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UpdatePayrollRequestDTO {
    private Long id;
    private String userId;
    private Long currentUserId;
    private String statementDate;
    private Double baseSalary;
    private Double bonuses;
    private Double allowances;
    private Double overtimePay;
    private Double grossSalary;
    private Double netSalary;
    private Double totalDeductions;
    private String remarks;

}



