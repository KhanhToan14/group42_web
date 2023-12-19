package com.web.recruitment.api;

import com.web.recruitment.api.dto.DeleteRequest;
import com.web.recruitment.api.dto.Enum.JobEnum.CurrencyEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.EmploymentTypeEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.ExperienceEnum;
import com.web.recruitment.api.dto.job.JobInsert;
import com.web.recruitment.api.dto.job.JobUpdate;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.JobMapper;
import com.web.recruitment.persistence.mapper.UserMapper;
import com.web.recruitment.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Tag(name = "Job API", description = "API for functions related to job")
@RestController
@Slf4j
@RequestMapping(value = "/v1/job")
@CrossOrigin(origins = "http://localhost:3000")
public class JobController {
    @Autowired
    private final JobService jobService;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final JobMapper jobMapper;
    @Autowired
    public JobController(JobService jobService, UserMapper userMapper, JobMapper jobMapper) {
        this.jobService = jobService;
        this.userMapper = userMapper;
        this.jobMapper = jobMapper;
    }
    @Operation(summary = "Insert job API", description = "Insert job")
    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> insertJob(
            @RequestParam(name = "companyId") int companyId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "employmentType", required = false) EmploymentTypeEnum employmentType,
            @RequestParam(name = "experience", required = false) ExperienceEnum experience,
            @RequestParam(name = "salaryFrom", required = false) Long salaryFrom,
            @RequestParam(name = "salaryTo", required = false) Long salaryTo,
            @RequestParam(name = "currency") CurrencyEnum currency,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "quantity") int quantity,
            @RequestParam(name = "dealTime") String dealTime
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(userMapper.selectRoleById(ownerId).equals("CANDIDATE")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        if(userMapper.selectEmployerAndCompanyIdById(ownerId) != companyId){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        JobInsert jobInsert = new JobInsert();
        jobInsert.setCompanyId(companyId);
        jobInsert.setName(name);
        jobInsert.setDescription(description);
        jobInsert.setLocation(location);
        jobInsert.setEmploymentType(employmentType);
        jobInsert.setExperience(experience);
        jobInsert.setSalaryFrom(salaryFrom);
        jobInsert.setSalaryTo(salaryTo);
        jobInsert.setCurrency(currency);
        jobInsert.setEmail(email);
        jobInsert.setPhone(phone);
        jobInsert.setQuantity(quantity);
        jobInsert.setDealTime(dealTime);

        resError = jobService.insert(jobInsert);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_INSERT_JOB)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if (resError.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Operation(summary = "Select job API", description = "select job")
    @GetMapping(path = "/select/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> selectJob(
            @PathVariable("id") int id
    ) throws Exception {
        Map<String, Object> response;
        JSONObject res;
        response = jobService.select(id);
        res = new JSONObject(response);
        if(response.containsKey(ERRORS)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


    @Operation(summary = "Update job API", description = "Update job")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> updateJob(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "companyId") int companyId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "location", required = false) String location,
            @RequestParam(name = "employmentType", required = false) EmploymentTypeEnum employmentType,
            @RequestParam(name = "experience", required = false) ExperienceEnum experience,
            @RequestParam(name = "salaryFrom", required = false) Long salaryFrom,
            @RequestParam(name = "salaryTo", required = false) Long salaryTo,
            @RequestParam(name = "currency") CurrencyEnum currency,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "quantity") int quantity,
            @RequestParam(name = "dealTime") String dealTime
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(userMapper.selectRoleById(ownerId).equals("CANDIDATE")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        if(userMapper.selectEmployerAndCompanyIdById(ownerId) != companyId){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        JobUpdate jobUpdate = new JobUpdate();
        jobUpdate.setId(id);
        jobUpdate.setCompanyId(companyId);
        jobUpdate.setName(name);
        jobUpdate.setDescription(description);
        jobUpdate.setLocation(location);
        jobUpdate.setEmploymentType(employmentType);
        jobUpdate.setExperience(experience);
        jobUpdate.setSalaryFrom(salaryFrom);
        jobUpdate.setSalaryTo(salaryTo);
        jobUpdate.setCurrency(currency);
        jobUpdate.setEmail(email);
        jobUpdate.setPhone(phone);
        jobUpdate.setQuantity(quantity);
        jobUpdate.setDealTime(dealTime);
        resError = jobService.update(jobUpdate);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_UPDATE_JOB)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        else if (resError.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Delete job API", description = "Delete job")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteJob(
            @PathVariable("id") int id
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(userMapper.selectRoleById(ownerId).equals("CANDIDATE")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        if(userMapper.selectEmployerAndCompanyIdById(ownerId) != jobMapper.selectCompanyIdByJobId(id)){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        resError = jobService.delete(id);
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_JOB)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Delete jobs API", description = "Delete jobs")
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteJobs(
            @RequestBody DeleteRequest deleteRequest
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(!userMapper.selectRoleById(ownerId).equals("ADMIN")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        resError = jobService.deleteChoice(deleteRequest.getDeleteIds());
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_JOB)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Get list job API", description = "get list job")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListJob(
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "sortBy", required = false, defaultValue = "time") String sortBy,
            @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType
    ) throws Exception{
        int pageSizeInt;
        int currentPageInt;
        if (!isNumeric(pageSize)) {
            pageSizeInt = 30;
        } else {
            pageSizeInt = Integer.parseInt(pageSize);
        }
        if (!isNumeric(currentPage)) {
            currentPageInt = 1;
        } else {
            currentPageInt = Integer.parseInt(currentPage);
        }
        JSONObject res;
        Map<String, Object> filter = new HashMap<>();
        filter.put(PAGE_SIZE, pageSizeInt);
        filter.put(CURRENT_PAGE, currentPageInt);
        filter.put(SORT_BY, sortBy);
        filter.put(SORT_TYPE, sortType);
        if (keyword == null || keyword.trim().equals("")) {
            filter.put(KEYWORD, null);
        }
        else {
            filter.put(KEYWORD, keyword);
        }
        Map<String, Object> responseBody;
        responseBody = jobService.listJob(filter);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @Operation(summary = "Get list job in company API", description = "get list job")
    @GetMapping(path = "/list_job_in_company", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListJobInCompany(
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "companyId") int companyId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "time") String sortBy,
            @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType
    ) throws Exception{
        int pageSizeInt;
        int currentPageInt;
        if (!isNumeric(pageSize)) {
            pageSizeInt = 30;
        } else {
            pageSizeInt = Integer.parseInt(pageSize);
        }
        if (!isNumeric(currentPage)) {
            currentPageInt = 1;
        } else {
            currentPageInt = Integer.parseInt(currentPage);
        }
        JSONObject res;
        Map<String, Object> filter = new HashMap<>();
        filter.put(PAGE_SIZE, pageSizeInt);
        filter.put(CURRENT_PAGE, currentPageInt);
        filter.put(SORT_BY, sortBy);
        filter.put(SORT_TYPE, sortType);
        filter.put(COMPANY_ID, companyId);
        Map<String, Object> responseBody;
        responseBody = jobService.listJob(filter);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    public int getOwnerIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getDetails();
        return user.getId();
    }
}
