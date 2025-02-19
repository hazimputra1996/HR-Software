package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateOvertimeRequestDTO {

    private String currentUserId;
    private String userId;
    private String overtimeDate;
    private String startTime;
    private String endTime;
    private String remarks;

}



