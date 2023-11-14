package com.web.recruitment.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private int id;
    private String code;
    private String name;
    private String website;
    private String description;
    private String logo;
    private String skills;
    private int delYn;
    private String createAt;
    private String updateAt;
}
