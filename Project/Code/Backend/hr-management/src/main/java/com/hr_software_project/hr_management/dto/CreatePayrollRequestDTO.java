package com.hr_software_project.hr_management.dto;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreatePayrollRequestDTO {
    private Date date;
    private Long userId;
    private Long currentUserId;
    private String remarks;
}



