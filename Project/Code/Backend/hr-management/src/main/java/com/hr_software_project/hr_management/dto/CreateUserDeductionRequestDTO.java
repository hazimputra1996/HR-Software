package com.hr_software_project.hr_management.dto;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateUserDeductionRequestDTO {

    private Long currentUserId;
    private Long userId;
    private Double amount;
    private Date startedDate;
    private Date endedDate;
    private Long deductionId;
}



