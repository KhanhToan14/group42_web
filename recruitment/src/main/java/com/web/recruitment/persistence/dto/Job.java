package com.web.recruitment.persistence.dto;

import com.web.recruitment.api.dto.Enum.JobEnum.CurrencyEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.EmploymentTypeEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.ExperienceEnum;
import com.web.recruitment.api.dto.Enum.LocationRequest;
import com.web.recruitment.api.dto.Enum.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
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
    private String email;
    private String phone;
    private int quantity;
    private String dealTime;
    private int status;
    private int delYn;
    private String createAt;
    private String updateAt;
}
