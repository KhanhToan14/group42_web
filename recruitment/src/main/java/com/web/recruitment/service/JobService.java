package com.web.recruitment.service;


import com.web.recruitment.api.dto.job.JobInsert;
import com.web.recruitment.api.dto.job.JobUpdate;

import java.util.List;
import java.util.Map;

public interface JobService {
    Map<String, Object> insert(JobInsert jobInsert) throws Exception;

    Map<String, Object> select(int id) throws Exception;

    Map<String, Object> listJob(Map<String, Object> filter) throws Exception;

    Map<String, Object> update(JobUpdate jobUpdate) throws Exception;

    Map<String, Object> delete(int id) throws Exception;

    Map<String, Object> deleteChoice(List<Integer> ids) throws Exception;

    Map<String, Object> listJobInCompany(Map<String, Object> filter) throws Exception;
}
