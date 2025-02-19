package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreateAuditTrailRequestDTO {

    private Long userId;
    private Long actionBy;
    private String action;
    private String module;
    private String oldValue;
    private String newValue;
    private String remarks;

}



