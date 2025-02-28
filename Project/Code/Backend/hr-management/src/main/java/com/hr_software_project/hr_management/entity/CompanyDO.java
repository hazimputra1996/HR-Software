package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private String status;
    private Integer cutOffDate;
}