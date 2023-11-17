package com.web.recruitment.service;

import com.web.recruitment.api.dto.Job.JobInsert;
import com.web.recruitment.persistence.mapper.CompanyMapper;
import com.web.recruitment.persistence.mapper.DepartmentMapper;
import com.web.recruitment.persistence.mapper.JobMapper;
import com.web.recruitment.utils.ValidationUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ValidationUtils.*;

@Slf4j
@Service
public class JobServiceImpl implements JobService{
    @Resource
    private final JobMapper jobMapper;
    @Resource
    private final CompanyMapper companyMapper;
    @Resource
    private final DepartmentMapper departmentMapper;

    public JobServiceImpl(JobMapper jobMapper, CompanyMapper companyMapper, DepartmentMapper departmentMapper) {
        this.jobMapper = jobMapper;
        this.companyMapper = companyMapper;
        this.departmentMapper = departmentMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insert(JobInsert jobInsert) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        int departmentId = jobInsert.getDepartmentId();
        if(departmentMapper.select(departmentId) == null){
            subResError.put(DEPARTMENT_ID, DEPARTMENT_NOT_FOUND);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        int companyId = jobInsert.getCompanyId();
        if(companyMapper.select(companyId) == null){
            subResError.put(COMPANY_ID, COMPANY_NOT_FOUND);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String name = jobInsert.getName();
        if(name == null || name.isBlank()){
            subResError.put(NAME, NAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            name = name.trim();
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put(NAME, name);
            if(jobMapper.selectByName(reqMap) != 0){
                subResError.put(NAME, NAME_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                jobInsert.setName(name);
            }
        }
        String description = jobInsert.getDescription();
        if(description != null && !description.trim().equals("")){
            jobInsert.setDescription(autoCorrectFormatName(description));
        }else {
            jobInsert.setDescription(null);
        }
        Long salaryFrom = jobInsert.getSalaryFrom();
        Long salaryTo = jobInsert.getSalaryTo();
        if (!validateSalary(salaryFrom, salaryTo)){
            subResError.put(SALARY, SALARY_INVALID);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        jobMapper.insert(jobInsert);
        resError.put(MESSAGE, SUCCESS_INSERT_JOB);
        return resError;
    }
}
