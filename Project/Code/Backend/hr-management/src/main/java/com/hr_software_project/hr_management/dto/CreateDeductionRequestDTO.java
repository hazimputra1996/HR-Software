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
public class CreateDeductionRequestDTO {

    private Long currentUserId;
    private String name;
    @Enumerated(EnumType.STRING)
    private DeductionType deductionType;
    private Double rate;
    private Double maxLimit;
    private String description;
}



