package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveTypeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., Annual Leave, Sick Leave
    private Long minServiceMonths; //Months
    private Long maxServiceMonths; //Months
    private Integer maxDays;
    private String expiryDate;
}