package com.web.recruitment.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicantForm {
    private int id;
    private int jobId;
    private int userId;
    private int cvId;
    private int delYn;
    private String createAt;
    private String updateAt;
}
