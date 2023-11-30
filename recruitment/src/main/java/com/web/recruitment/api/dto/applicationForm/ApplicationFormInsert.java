package com.web.recruitment.api.dto.applicationForm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFormInsert {
    @JsonIgnore
    private int id;
    private int jobId;
    private int userId;
    private String CV;
}
