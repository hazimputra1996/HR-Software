package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "salary_statements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStatementDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDO user;

    private Date statementDate;
    private Double base_salary;
    private String remarks;
}

