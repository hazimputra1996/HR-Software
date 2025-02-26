package com.hr_software_project.hr_management.dto;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreatePublicHolidayRequestDTO {
    private Date date;
    private String name;
    private String country;
    private String state;
    private Boolean recurring;
    private Long currentUserId;
}



