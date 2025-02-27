package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateUserRequestDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String role;
    private Double salary;
    private Double daily_working_hours;
    private Double number_of_working_days_per_week;
    private String epfNumber;
    private String socsoNumber;
    private String incomeTaxNumber;
    private String registrationNumber;
    private String employeeNumber;
    private String hireDate;
    private Integer leaveBalance;
    private Long supervisorId;
    private Long currentUserId;
}



