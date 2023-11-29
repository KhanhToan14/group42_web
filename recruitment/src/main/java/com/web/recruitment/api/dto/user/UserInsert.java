package com.web.recruitment.api.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.recruitment.api.dto.Enum.UserEnum.GenderEnum;
import com.web.recruitment.api.dto.Enum.UserEnum.RoleEnum;
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
public class UserInsert {
    @JsonIgnore
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    private Integer companyId;
    private String skills;
}
