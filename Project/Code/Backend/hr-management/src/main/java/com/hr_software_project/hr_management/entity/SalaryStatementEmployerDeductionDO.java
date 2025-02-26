package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salary_statements_employer_deductions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStatementEmployerDeductionDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDO user;

    @ManyToOne
    @JoinColumn(name = "salary_statement_id", nullable = false)
    private SalaryStatementDO salaryStatement;

    @ManyToOne
    @JoinColumn(name = "deduction_id", nullable = false)
    private DeductionDO deduction;

    private String name;
    private Double amount;
    private String remarks;
}

