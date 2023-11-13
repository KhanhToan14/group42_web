package com.web.recruitment.api.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyInformation {
    private String name;
    private String website;
    private String description;
    private String logoUrl;
    private String skills;
}
