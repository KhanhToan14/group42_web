package com.web.recruitment.service;


import com.web.recruitment.api.dto.Job.JobInsert;

import java.util.Map;

public interface JobService {
    Map<String, Object> insert(JobInsert jobInsert) throws Exception;
}
