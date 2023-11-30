package com.web.recruitment.persistence.mapper;

import com.web.recruitment.api.dto.applicationForm.ApplicationFormInsert;
import com.web.recruitment.api.dto.applicationForm.ApplicationFormUpdate;
import com.web.recruitment.persistence.dto.ApplicationForm;

import java.util.List;
import java.util.Map;

public interface ApplicationFormMapper {
    List<ApplicationForm> list(Map<String, Object> reqMap) throws Exception;

    ApplicationForm select(int id) throws Exception;

    int insert(ApplicationFormInsert applicationFormInsert) throws Exception;

    int update(ApplicationFormUpdate applicationFormUpdate) throws Exception;

    int delete(int id) throws Exception;

    int deleteChoice(List<Integer> id) throws Exception;
}
