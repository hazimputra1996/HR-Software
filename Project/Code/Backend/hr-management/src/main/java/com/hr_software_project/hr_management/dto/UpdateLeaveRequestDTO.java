package com.hr_software_project.hr_management.dto;


import com.hr_software_project.hr_management.entity.LeaveDO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UpdateLeaveRequestDTO extends LeaveDO {
    private Long currentUserId;
}



