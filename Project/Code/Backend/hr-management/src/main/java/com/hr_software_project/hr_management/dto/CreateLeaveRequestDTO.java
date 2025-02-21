package com.hr_software_project.hr_management.dto;


import com.hr_software_project.hr_management.entity.LeaveTypeDO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateLeaveRequestDTO {
    private String startDate;
    private String endDate;
    private LeaveTypeDO leaveType;
    private Long userId;
    private Long currentUserId;
    private Boolean autoApproved;
}



