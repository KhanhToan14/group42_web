package com.web.recruitment.service;

import com.web.recruitment.api.dto.job.JobInsert;
import com.web.recruitment.api.dto.job.JobUpdate;
import com.web.recruitment.persistence.dto.Job;
import com.web.recruitment.persistence.mapper.CompanyMapper;
import com.web.recruitment.persistence.mapper.DepartmentMapper;
import com.web.recruitment.persistence.mapper.JobMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
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
        String phone = jobInsert.getPhone();
        if(phone == null || phone.isBlank()){
            subResError.put(PHONE, PHONE_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            jobInsert.setPhone(validateVietnamesePhoneNumber(phone));
            if(jobInsert.getPhone() == null){
                subResError.put(PHONE, PHONE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String email = jobInsert.getEmail();
        if(email == null || email.isBlank()){
            subResError.put(EMAIL, EMAIL_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            email = email.trim();
            Map<String, Object> map = new HashMap<>();
            map.put(EMAIL, email);
            if(!validateEmail(email)){
                subResError.put(EMAIL, EMAIL_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                jobInsert.setEmail(email);
            }
        }
        String dealTime = jobInsert.getDealTime();
        if (dealTime == null || dealTime.isBlank()) {
            subResError.put(DEAL_TIME, DEAL_TIME_NOT_NULL_ERROR);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            dealTime = dealTime.trim();
            try {
                LocalDate dt = LocalDate.parse(dealTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate now = LocalDate.now();
                if (ChronoUnit.DAYS.between(dt, now) > 0) {
                    subResError.put(DEAL_TIME, DEAL_TIME_MUST_AFTER_NOW_ERROR);
                    resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                    resError.put(ERRORS, subResError);
                    return resError;
                } else {
                    jobInsert.setDealTime(dealTime);
                }
            } catch (DateTimeParseException ex) {
                subResError.put(DEAL_TIME, DEAL_TIME_INVALID_FORMAT_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        jobMapper.insert(jobInsert);
        resError.put(MESSAGE, SUCCESS_INSERT_JOB);
        return resError;
    }

    @Override
    public Map<String, Object> update(JobUpdate jobUpdate) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(jobUpdate.getId() == null){
            subResError.put(ID, ID_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(departmentMapper.select(jobUpdate.getId()) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        int departmentId = jobUpdate.getDepartmentId();
        if(departmentMapper.select(departmentId) == null){
            subResError.put(DEPARTMENT_ID, DEPARTMENT_NOT_FOUND);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String name = jobUpdate.getName();
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
                jobUpdate.setName(name);
            }
        }
        String description = jobUpdate.getDescription();
        if(description != null && !description.trim().equals("")){
            jobUpdate.setDescription(autoCorrectFormatName(description));
        }else {
            jobUpdate.setDescription(null);
        }
        Long salaryFrom = jobUpdate.getSalaryFrom();
        Long salaryTo = jobUpdate.getSalaryTo();
        if (!validateSalary(salaryFrom, salaryTo)){
            subResError.put(SALARY, SALARY_INVALID);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String phone = jobUpdate.getPhone();
        if(phone == null || phone.isBlank()){
            subResError.put(PHONE, PHONE_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            jobUpdate.setPhone(validateVietnamesePhoneNumber(phone));
            if(jobUpdate.getPhone() == null){
                subResError.put(PHONE, PHONE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String email = jobUpdate.getEmail();
        if(email == null || email.isBlank()){
            subResError.put(EMAIL, EMAIL_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            email = email.trim();
            Map<String, Object> map = new HashMap<>();
            map.put(EMAIL, email);
            if(!validateEmail(email)){
                subResError.put(EMAIL, EMAIL_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                jobUpdate.setEmail(email);
            }
        }
        String dealTime = jobUpdate.getDealTime();
        if (dealTime == null || dealTime.isBlank()) {
            subResError.put(DEAL_TIME, DEAL_TIME_NOT_NULL_ERROR);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            dealTime = dealTime.trim();
            try {
                LocalDate dt = LocalDate.parse(dealTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate now = LocalDate.now();
                if (ChronoUnit.DAYS.between(dt, now) > 0) {
                    subResError.put(DEAL_TIME, DEAL_TIME_MUST_AFTER_NOW_ERROR);
                    resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                    resError.put(ERRORS, subResError);
                    return resError;
                } else {
                    jobUpdate.setDealTime(dealTime);
                }
            } catch (DateTimeParseException ex) {
                subResError.put(DEAL_TIME, DEAL_TIME_INVALID_FORMAT_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        jobMapper.update(jobUpdate);
        resError.put(MESSAGE, SUCCESS_UPDATE_JOB);
        return resError;
    }
    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        Job job = jobMapper.select(id);
        if(job == null) {
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            response.put(JOB, job);
            return response;
        }
    }

    @Override
    public Map<String, Object> listDepartment(Map<String, Object> filter) throws Exception{
        List<Job> retList;
        Map<String, Object> reqInfo = new HashMap<>();
        int pageSize = (int) filter.get(PAGE_SIZE);
        int currentPage = (int) filter.get(CURRENT_PAGE);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int limit = pageSize;
        int offset = pageSize * (currentPage - 1);
        filter.put(LIMIT, limit);
        filter.put(OFFSET, offset);
        String sortBy = (String) filter.get(SORT_BY);
        String sortType = (String) filter.get(SORT_TYPE);
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = TIME;
        } else {
            sortBy = sortBy.trim();
            if (!sortBy.equals(NAME) && !sortBy.equals(TIME)) {
                sortBy = TIME;
            }
        }
        if (sortType == null || sortType.isBlank()) {
            if (sortBy.equals(TIME)) {
                sortType = DESC;
            } else {
                sortType = ASC;
            }
        } else {
            sortType = sortType.trim();
            if (!sortType.equals(ASC) && !sortType.equals(DESC)) {
                sortType = ASC;
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = jobMapper.list(filter);
        int total = retList.size();
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }

    @Override
    public Map<String, Object> delete(int id) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(jobMapper.select(id) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        jobMapper.delete(id);
        resError.put(MESSAGE, SUCCESS_DELETE_JOB);
        return resError;
    }

    @Override
    public Map<String, Object> deleteChoice(List<Integer> ids) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        ArrayList<Integer> idsDeleteSuccess = new ArrayList<>();
        ArrayList<Integer> idsDeleteNotSuccess = new ArrayList<>();
        int validId;
        int idsSize = ids.size();
        if(idsSize > 0){
            for(Integer id : ids){
                if(jobMapper.select(id) == null){
                    idsDeleteNotSuccess.add(id);
                } else {
                    validId = id;
                    idsDeleteSuccess.add(validId);
                    jobMapper.delete(id);
                }
            }
        } else {
            subResError.put(DELETE_IDS, DELETE_IDS_MUST_NOT_NULL_OR_EMPTY);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(idsDeleteSuccess.size() > 0){
            if (idsDeleteNotSuccess.size() > 0){
                resError.put(LIST_ID_CAN_NOT_DELETE, idsDeleteNotSuccess);
                resError.put(LIST_ID_DELETED, idsDeleteSuccess);
                resError.put(MESSAGE, SUCCESS_DELETE_JOB);
            } else{
                resError.put(MESSAGE, SUCCESS_DELETE_JOB);
            }
        } else {
            subResError.put(DELETE_IDS, IDS_INVALID);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
        }
        return resError;
    }
    @Override
    public Map<String, Object> listJobInCompany(Map<String, Object> filter) throws Exception{
        List<Job> retList;
        Map<String, Object> reqInfo = new HashMap<>();
        int pageSize = (int) filter.get(PAGE_SIZE);
        int currentPage = (int) filter.get(CURRENT_PAGE);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int total = jobMapper.total(filter);
        int limit = pageSize;
        int offset = pageSize * (currentPage - 1);
        filter.put(LIMIT, limit);
        filter.put(OFFSET, offset);
        String sortBy = (String) filter.get(SORT_BY);
        String sortType = (String) filter.get(SORT_TYPE);
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = TIME;
        } else {
            sortBy = sortBy.trim();
            if (!sortBy.equals(NAME) && !sortBy.equals(TIME)) {
                sortBy = TIME;
            }
        }
        if (sortType == null || sortType.isBlank()) {
            if (sortBy.equals(TIME)) {
                sortType = DESC;
            } else {
                sortType = ASC;
            }
        } else {
            sortType = sortType.trim();
            if (!sortType.equals(ASC) && !sortType.equals(DESC)) {
                sortType = ASC;
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = jobMapper.list(filter);
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }
}
