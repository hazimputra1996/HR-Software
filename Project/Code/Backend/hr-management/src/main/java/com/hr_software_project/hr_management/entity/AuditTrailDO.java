package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;
import com.hr_software_project.hr_management.enums.AuditModule;

import java.util.Date;

@Entity
@Table(name = "audit_trail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditTrailDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDO user; // The affected user

    @ManyToOne
    @JoinColumn(name = "action_by")
    private UserDO actionBy; // The person who performed the action

    private String action; // e.g., "Salary Adjustment", "Leave Approved"

    @Enumerated(EnumType.STRING)
    private AuditModule module; // leave, salary, role, etc.

    private String oldValue;
    private String newValue;
    private Date actionTimestamp = new Date();
    private String remarks;
}
