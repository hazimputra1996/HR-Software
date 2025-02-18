package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class LeaveBalanceDTO {
    private Long userId;
    private String leaveType;
    private Integer balance;
}



