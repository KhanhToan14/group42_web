package com.web.recruitment.persistence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.recruitment.api.dto.Enum.UserEnum.GenderEnum;
import com.web.recruitment.api.dto.Enum.UserEnum.RoleEnum;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
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
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
    private Integer companyId;
    private String skills;
    private int status;
    @JsonIgnore
    private int delYn;
    private String createAt;
    private String updateAt;
    @JsonIgnore
    private String emailVerifiedAt;
    @JsonIgnore
    private String otp;
    @JsonIgnore
    private String otpTimeSent;
}
