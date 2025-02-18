package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateLeaveRequestDTO {
    private String startDate;
    private String endDate;
    private String leaveType;
    private Long userId;
    private Long currentUserId;
}



