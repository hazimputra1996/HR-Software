package com.hr_software_project.hr_management.dto;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateOvertimeRequestDTO {

    private Long currentUserId;
    private Long userId;
    private Date overtimeDate;
    private Date startTime;
    private Date endTime;
    private String remarks;
    private Double rate = 1.5;

}



