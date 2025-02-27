package com.hr_software_project.hr_management.dto;


import com.hr_software_project.hr_management.enums.DeductionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Double amount;
    @Enumerated(EnumType.STRING)
    private DeductionType allowanceType;
    private Date dateStarted;
    private Date dateEnded;
    private String remarks;

}



