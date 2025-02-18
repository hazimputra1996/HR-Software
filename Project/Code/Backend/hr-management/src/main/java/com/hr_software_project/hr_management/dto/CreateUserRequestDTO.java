package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateUserRequestDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private Double salary;
    private String epfNumber;
    private String socsoNumber;
    private String incomeTaxNumber;
    private String hireDate;
    private Integer leaveBalance;
    private Long supervisorId;
    private Long currentUserId;
}



