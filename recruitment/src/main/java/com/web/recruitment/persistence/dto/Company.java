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
    private String avatar;
    private String banner;
    private String website;
    private String phone;
    private String email;
    private String description;
    private String skills;
    private int status;
    private int delYn;
    private String createAt;
    private String updateAt;
}
