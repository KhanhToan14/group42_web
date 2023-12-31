package com.web.recruitment.persistence.mapper;

import com.web.recruitment.api.dto.job.JobInsert;
import com.web.recruitment.api.dto.job.JobUpdate;
import com.web.recruitment.persistence.dto.Job;

import java.util.List;
import java.util.Map;

public interface JobMapper {
    int insert(JobInsert jobInsert) throws Exception;

    int update(JobUpdate jobUpdate) throws Exception;

    List<Job> list(Map<String, Object> reqMap) throws Exception;

    Job select(int id) throws Exception;

    int total(Map<String, Object> reqMap) throws Exception;

    int selectByName(Map<String, Object> reqMap) throws Exception;

    int delete(int id) throws Exception;

    int deleteChoice(List<Integer> id) throws Exception;

    List<Job> listJobInCompany(Map<String, Object> reqMap) throws Exception;

    int selectCompanyIdByJobId(int id);

    int selectCompanyIdByDepartmentId(int departmentId);

    String selectNameById(int id);

    Job checkNameUpdate(Map<String, Object> reqMap);

    List<Job> listJobByCompanyId(int companyId);
}
