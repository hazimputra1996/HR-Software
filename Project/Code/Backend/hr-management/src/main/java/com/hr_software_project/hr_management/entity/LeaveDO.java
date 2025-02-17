package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;
import com.hr_software_project.hr_management.enums.LeaveStatus;

import java.util.Date;

@Entity
@Table(name = "leaves")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDO user;

    @ManyToOne
    @JoinColumn(name = "leave_type_id", nullable = false)
    private LeaveTypeDO leaveType;

    private Date startDate;
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    private Date appliedOn = new Date();

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private UserDO approvedBy;

    private Boolean autoApproved = false;
}
