package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "deductions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployerDeductionDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDO user; // The affected user


    @ManyToOne
    @JoinColumn(name = "deduction_id")
    private DeductionDO deduction; // The deduction type

    private Date startedDate;

    private Date endedDate;

    private Double amount; // If use different rate/amount
}

