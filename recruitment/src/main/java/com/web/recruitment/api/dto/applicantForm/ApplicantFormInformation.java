package com.web.recruitment.api.dto.applicantForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantFormInformation {
    private int id;
    private int jobId;
    private String username;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String phone;
    private String email;
    private String address;
    private String cvName;
}
