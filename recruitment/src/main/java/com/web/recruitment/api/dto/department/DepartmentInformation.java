package com.web.recruitment.api.dto.department;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInformation {
    private int id;
    private int companyId;
    private String name;
    private String description;
}