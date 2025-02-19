package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateDeductionForUserRequestDTO {

    private String currentUserId;
    private String userId;
    private String deductionId;
    private Double startedDate;
    private String endDate;
    private String rate; // if using different rate
}



