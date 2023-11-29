package com.web.recruitment.api;

import com.web.recruitment.api.dto.DeleteRequest;
import com.web.recruitment.api.dto.Enum.JobEnum.CurrencyEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.EmploymentTypeEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.ExperienceEnum;
import com.web.recruitment.api.dto.job.JobInsert;
import com.web.recruitment.api.dto.job.JobUpdate;
import com.web.recruitment.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Tag(name = "Job API", description = "API for functions related to job")
@RestController
@Slf4j
@RequestMapping(value = "/v1/job")
public class JobController {
    @Resource
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @Operation(summary = "Insert job API", description = "Insert job")
    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertJob(
            @RequestParam(name = "departmentId") int departmentId,
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
        JSONObject res;
        Map<String, Object> resError;
        JobInsert jobInsert = new JobInsert();
        jobInsert.setDepartmentId(departmentId);
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
        if(response.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


    @Operation(summary = "Update job API", description = "Update job")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateJob(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "departmentId") int departmentId,
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
        JSONObject res;
        Map<String, Object> resError;
        JobUpdate jobUpdate = new JobUpdate();
        jobUpdate.setId(id);
        jobUpdate.setDepartmentId(departmentId);
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
    public ResponseEntity<Object> deleteJob(
            @PathVariable("id") int id
    ) throws Exception{
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
    public ResponseEntity<Object> deleteJobs(
            @RequestBody DeleteRequest deleteRequest
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = jobService.deleteChoice(deleteRequest.getDeleteIds());
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_JOB)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
