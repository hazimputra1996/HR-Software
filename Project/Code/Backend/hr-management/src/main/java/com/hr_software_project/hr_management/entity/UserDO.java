package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;
import com.hr_software_project.hr_management.enums.Role;

import java.util.Date;
import java.util.List;

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

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, EMPLOYEE, SUPERVISOR

    private Double salary;
    private String epfNumber;
    private String socsoNumber;
    private String incomeTaxNumber;
    private Date hireDate;

    private Integer leaveBalance = 0;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private UserDO supervisor;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LeaveDO> leaves;

}
