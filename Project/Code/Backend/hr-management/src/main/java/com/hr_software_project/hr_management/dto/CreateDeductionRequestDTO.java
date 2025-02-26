package com.hr_software_project.hr_management.dto;


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
    private String deductionType;
    private Double rate;
    private Double maxLimit;
    private String description;
}



