package com.hr_software_project.hr_management.entity;

import com.hr_software_project.hr_management.enums.DeductionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "allowance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllowanceDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDO user;

    private String name;
    private Boolean oneTimeBonus;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private DeductionType allowanceType;

    private Date dateStarted;
    private Date dateEnded;
    private String remarks;

    private Date processedOn = new Date();
}

