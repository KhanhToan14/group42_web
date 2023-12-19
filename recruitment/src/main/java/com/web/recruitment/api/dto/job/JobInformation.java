package com.web.recruitment.api.dto.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobInformation {
    private int id;
    private int companyId;
    private String name;
    private String description;
    private String location;
    private String employmentType;
    private String experience;
    private Long salaryFrom;
    private Long salaryTo;
    private String currency;
    private String email;
    private String phone;
    private int quantity;
    private String dealTime;
}
