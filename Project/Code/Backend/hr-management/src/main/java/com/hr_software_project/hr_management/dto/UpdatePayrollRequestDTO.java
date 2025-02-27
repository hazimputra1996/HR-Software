package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UpdatePayrollRequestDTO {
    private Long id;
    private Long currentUserId;
    private String remarks;

}



