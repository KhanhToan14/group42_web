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
    private String id;
    private String code;
    private String name;
    private String avatar;
    private String banner;
    private String website;
    private String phone;
    private String email;
    private String description;
    private String skills;
}
