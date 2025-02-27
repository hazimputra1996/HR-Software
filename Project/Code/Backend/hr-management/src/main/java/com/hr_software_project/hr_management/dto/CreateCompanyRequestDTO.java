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
public class CreateCompanyRequestDTO {

    private String companyName;
    private String companyRegistrationNumber;
    private String companyNumber;
    private String companyAddress;
    private String companyCity;
    private String companyState;
    private String companyPostalCode;
    private String companyCountry;
    private String companyPhoneNumber;
    private String companyEmail;
    private String companyWebsite;
    private String companyFaxNumber;
    private Long currentUserId;
    private String cutOffDate;

}



