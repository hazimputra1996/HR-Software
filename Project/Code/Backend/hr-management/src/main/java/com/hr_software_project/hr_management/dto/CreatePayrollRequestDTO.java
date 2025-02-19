package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreatePayrollRequestDTO {
    private String date;
    private String userId;
    private String currentUserId;
}



