package com.web.recruitment.api.dto.job;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.recruitment.api.dto.Enum.JobEnum.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobInsert {
    @JsonIgnore
    private int id;
    private int departmentId;
    private String name;
    private String description;
    private String location;
    @Enumerated(EnumType.STRING)
    private EmploymentTypeEnum employmentType;
    @Enumerated(EnumType.STRING)
    private ExperienceEnum experience;
    private Long salaryFrom;
    private Long salaryTo;
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;
}
