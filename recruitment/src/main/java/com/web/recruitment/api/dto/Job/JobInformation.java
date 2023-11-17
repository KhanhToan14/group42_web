package com.web.recruitment.api.dto.Job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobInformation {
    private int id;
    private int departmentId;
    private int companyId;
    private String name;
    private String description;
    private String location;
    private String employmentType;
    private String experience;
    private Long salaryFrom;
    private Long salaryTo;
    private String currency;
}
