package com.web.recruitment.api.dto.applicationForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFormUpdate {
    private int id;
    private int jobId;
    private int userId;
    private String CV;
}
