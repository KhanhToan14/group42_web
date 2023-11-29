package com.web.recruitment.api.dto.job;

import com.web.recruitment.api.dto.Enum.JobEnum.CurrencyEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.EmploymentTypeEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.ExperienceEnum;
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
public class JobUpdate {
    private Integer id;
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
    private String email;
    private String phone;
    private int quantity;
    private String dealTime;
}
