package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "salary_statements_allowances")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStatementAllowanceDO {

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
    @JoinColumn(name = "allowance_id", nullable = false)
    private AllowanceDO allowance;

    private String name;
    private Double amount;
}

