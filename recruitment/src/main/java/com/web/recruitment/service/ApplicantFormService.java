package com.web.recruitment.service;


import com.web.recruitment.api.dto.applicantForm.ApplicantFormInsert;
import com.web.recruitment.api.dto.applicantForm.ApplicantFormUpdate;

import java.util.List;
import java.util.Map;

public interface ApplicantFormService {
    Map<String, Object> select(int id) throws Exception;

    Map<String, Object> listApplicantForm(Map<String, Object> filter) throws Exception;

    Map<String, Object> insert(ApplicantFormInsert applicantFormInsert) throws Exception;

    Map<String, Object> update(ApplicantFormUpdate applicantFormUpdate) throws Exception;

    Map<String, Object> delete(int id) throws Exception;

    Map<String, Object> deleteChoice(List<Integer> ids) throws Exception;
}
