package com.web.recruitment.persistence.mapper;

import com.web.recruitment.api.dto.applicantForm.ApplicantFormInsert;
import com.web.recruitment.api.dto.applicantForm.ApplicantFormUpdate;
import com.web.recruitment.persistence.dto.ApplicantForm;

import java.util.List;
import java.util.Map;

public interface ApplicantFormMapper {
    List<ApplicantForm> list(Map<String, Object> reqMap) throws Exception;

    ApplicantForm select(int id) throws Exception;

    int insert(ApplicantFormInsert applicantFormInsert) throws Exception;

    int update(ApplicantFormUpdate applicantFormUpdate) throws Exception;

    int delete(int id) throws Exception;

    int deleteChoice(List<Integer> id) throws Exception;
}
