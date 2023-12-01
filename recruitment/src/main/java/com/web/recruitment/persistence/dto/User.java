package com.web.recruitment.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String password;
    private String role;
    private Integer companyId;
    private String skills;
    private int status;
    private int delYn;
    private String createAt;
    private String updateAt;
}
