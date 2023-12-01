package com.web.recruitment.service;

import com.web.recruitment.api.dto.applicantForm.ApplicantFormInsert;
import com.web.recruitment.api.dto.applicantForm.ApplicantFormUpdate;
import com.web.recruitment.persistence.dto.ApplicantForm;
import com.web.recruitment.persistence.mapper.ApplicantFormMapper;
import com.web.recruitment.persistence.mapper.JobMapper;
import com.web.recruitment.persistence.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;

@Slf4j
@Service
public class ApplicantFormServiceImpl implements ApplicantFormService {
    @Resource
    private final ApplicantFormMapper applicantFormMapper;

    @Resource
    private final JobMapper jobMapper;
    @Resource
    private final UserMapper userMapper;

    public ApplicantFormServiceImpl(ApplicantFormMapper applicantFormMapper, JobMapper jobMapper, UserMapper userMapper) {
        this.applicantFormMapper = applicantFormMapper;
        this.jobMapper = jobMapper;
        this.userMapper = userMapper;
    }
    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        ApplicantForm applicantForm = applicantFormMapper.select(id);
        if(applicantForm == null) {
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            response.put(APPLICANT_FORM, applicantForm);
            return response;
        }
    }
    @Override
    public Map<String, Object> listApplicantForm(Map<String, Object> filter) throws Exception{
        List<ApplicantForm> retList;
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
        if (sortType == null || sortType.isBlank()) {
            sortType = "desc";
        } else {
            sortType = sortType.trim();
            if (!sortType.equals("asc") && !sortType.equals("desc")) {
                sortType = "asc";
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = applicantFormMapper.list(filter);
        int total = retList.size();
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }
    @Override
    public Map<String, Object> insert(ApplicantFormInsert applicantFormInsert) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        int jobId = applicantFormInsert.getJobId();
        if(jobMapper.select(jobId) == null){
            subResError.put(JOB_ID, jobId);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        int userId = applicantFormInsert.getUserId();
        if(userMapper.select(userId) == null) {
            subResError.put(USER_ID, userId);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        applicantFormMapper.insert(applicantFormInsert);
        resError.put(MESSAGE, SUCCESS_INSERT_APPLICANT_FORM);
        return resError;
    }
    @Override
    public Map<String, Object> update(ApplicantFormUpdate applicantFormUpdate) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        int id = applicantFormUpdate.getId();
        if(applicantFormMapper.select(id) == null){
            if(jobMapper.select(id) == null){
                subResError.put(ID, id);
                resError.put(MESSAGE, NOT_FOUND_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        int jobId = applicantFormUpdate.getJobId();
        if(jobMapper.select(jobId) == null){
            subResError.put(JOB_ID, jobId);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        int userId = applicantFormUpdate.getUserId();
        if(userMapper.select(userId) == null) {
            subResError.put(USER_ID, userId);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        applicantFormMapper.update(applicantFormUpdate);
        resError.put(MESSAGE, SUCCESS_UPDATE_APPLICANT_FORM);
        return null;
    }
    @Override
    public Map<String, Object> delete(int id) throws Exception{
        return null;
    }
    @Override
    public Map<String, Object> deleteChoice(List<Integer> ids) throws Exception{
        return null;
    }
}
