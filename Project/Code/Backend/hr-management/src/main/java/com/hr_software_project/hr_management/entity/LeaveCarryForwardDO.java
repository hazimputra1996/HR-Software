package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "leave_carry_forward")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveCarryForwardDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDO user;

    private Integer previousYear;
    private Integer carriedDays;
    private Date expiryDate;
    private Date processedOn = new Date();
}

