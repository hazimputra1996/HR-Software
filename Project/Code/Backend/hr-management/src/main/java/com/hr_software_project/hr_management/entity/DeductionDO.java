package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;
import com.hr_software_project.hr_management.enums.DeductionType;

@Entity
@Table(name = "deductions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeductionDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // EPF, SOCSO, etc.

    private String deductionType; // Percentage or Fixed

    private Double rate;
    private Double maxLimit;
    private String description;
    private Boolean isEmployeeDeduction;
}

