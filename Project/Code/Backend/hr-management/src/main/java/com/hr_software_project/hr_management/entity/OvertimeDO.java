package com.hr_software_project.hr_management.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "overtime")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OvertimeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDO user;

    private Date overtimeDate;
    private Double hoursWorked;
    private Double overtimeRate;
    private Double totalOvertime;
    private String remarks;
}

