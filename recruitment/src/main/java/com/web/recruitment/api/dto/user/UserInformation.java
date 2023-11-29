package com.web.recruitment.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {
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
    private String role;
    private Integer companyId;
    private String skills;
}
