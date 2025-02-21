package com.hr_software_project.hr_management.dto;


import com.hr_software_project.hr_management.enums.AuditModule;
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
    private AuditModule module;
    private String oldValue;
    private String newValue;
    private String remarks;

}



