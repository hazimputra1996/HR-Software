package com.hr_software_project.hr_management.dto;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateAllowanceRequestDTO {

    private Long currentUserId;
    private Long userId;
    private String name;
    private Boolean oneTimeBonus;
    private Integer amount;
    private String allowanceType;
    private Date dateStarted;
    private Date dateEnded;
    private String remarks;

}



