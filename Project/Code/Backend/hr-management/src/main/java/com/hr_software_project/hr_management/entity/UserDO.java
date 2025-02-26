package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;
import com.hr_software_project.hr_management.enums.Role;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String first_name;
    private String last_name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, EMPLOYEE, SUPERVISOR

    private Double salary;
    private Double daily_working_hours;
    private Double number_of_working_days_per_week;

    private String epf_number;
    private String socso_number;
    private String income_tax_number;
    private Date hire_date;

    private Integer leave_balance = 0;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private UserDO supervisor;


}
