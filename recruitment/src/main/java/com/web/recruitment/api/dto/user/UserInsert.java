package com.web.recruitment.api.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInsert {
    @JsonIgnore
    private int id;
    private int companyId;
    private String username;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String password;
    private String role;
    private String skills;
}
