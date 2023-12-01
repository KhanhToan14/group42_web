package com.web.recruitment.api.dto.applicantForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantFormUpdate {
    private int id;
    private int jobId;
    private int userId;
    private int cvId;
}
