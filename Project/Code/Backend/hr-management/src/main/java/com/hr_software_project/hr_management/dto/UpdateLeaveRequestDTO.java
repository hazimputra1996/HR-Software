package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class UpdateLeaveRequestDTO extends CreateLeaveRequestDTO {
    private Long id;
}



