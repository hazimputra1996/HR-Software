package com.hr_software_project.hr_management.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CreatePublicHolidayRequestDTO {
    private String date;
    private String name;
    private String country;
    private String state;
    private Boolean recurring;
}



