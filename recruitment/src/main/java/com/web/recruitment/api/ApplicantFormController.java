package com.web.recruitment.api;

import com.web.recruitment.api.dto.DeleteRequest;
import com.web.recruitment.api.dto.applicantForm.ApplicantFormInsert;
import com.web.recruitment.api.dto.applicantForm.ApplicantFormUpdate;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.ApplicantFormMapper;
import com.web.recruitment.persistence.mapper.UserMapper;
import com.web.recruitment.service.ApplicantFormService;
import com.web.recruitment.service.CVService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Tag(name = "Applicant form API", description = "API for functions related to applicant form")
@RestController
@Slf4j
@RequestMapping(value = "/v1/applicant_form")
@CrossOrigin(origins = "http://localhost:3000")
public class ApplicantFormController {
    @Autowired
    private final ApplicantFormService applicantFormService;
    @Autowired
    private final CVService cvService;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final ApplicantFormMapper applicantFormMapper;

    public ApplicantFormController(ApplicantFormService applicantFormService, CVService cvService, UserMapper userMapper, ApplicantFormMapper applicantFormMapper) {
        this.applicantFormService = applicantFormService;
        this.cvService = cvService;
        this.userMapper = userMapper;
        this.applicantFormMapper = applicantFormMapper;
    }

    @Operation(summary = "Select applicant form API", description = "select applicant form")
    @GetMapping(path = "/select/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> selectApplicantForm(
            @PathVariable("id") int id
    ) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(userMapper.selectRoleById(ownerId).equals("CANDIDATE")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        if(applicantFormMapper.selectCompanyIdByJobId(applicantFormMapper.selectJobIdById(id)) != userMapper.selectEmployerAndCompanyIdById(ownerId)){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        Map<String, Object> response;
        JSONObject res;
        response = applicantFormService.select(id);
        res = new JSONObject(response);
        if(response.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get list applicant form API", description = "get list applicant form")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> getListApplicantForm(
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "jobId") int jobId,
            @RequestParam(value = "sortBy", required = false, defaultValue = "time") String sortBy,
            @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(userMapper.selectRoleById(ownerId).equals("CANDIDATE")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        if(applicantFormMapper.selectCompanyIdByJobId(jobId) != userMapper.selectEmployerAndCompanyIdById(ownerId)){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
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
        filter.put(JOB_ID, jobId);
        Map<String, Object> responseBody;
        responseBody = applicantFormService.listApplicantForm(filter);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Insert applicant form API", description = "Insert applicant form")
    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> insertApplicantForm(
            @RequestParam(name = "jobId") int jobId,
            @RequestParam(name = "file") MultipartFile file
    ) throws Exception {
        int ownerId = this.getOwnerIdFromToken();
        JSONObject res1;
        Map<String, Object> resError1;
        Map<String, Object> resError2;
        resError1 = cvService.store(file);
        res1 = new JSONObject(resError1);
        if (resError1.get(MESSAGE).equals(SUCCESS_STORE_FILE)){
            ApplicantFormInsert applicantFormInsert = new ApplicantFormInsert();
            applicantFormInsert.setJobId(jobId);
            applicantFormInsert.setUserId(ownerId);
            applicantFormInsert.setCvId((int)resError1.get(ID));
            JSONObject res2;
            resError2 = applicantFormService.insert(applicantFormInsert);
            res2 = new JSONObject(resError2);
            if(resError2.get(MESSAGE).equals(SUCCESS_INSERT_APPLICANT_FORM)){
                return new ResponseEntity<>(res2, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(res2, HttpStatus.NOT_FOUND);
            }
        } else{
            return new ResponseEntity<>(res1, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @Operation(summary = "Update applicant form API", description = "Update applicant form")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> updateApplicantForm(
            @RequestParam(name = "id") int id,
            @RequestParam(name = "jobId") int jobId,
            @RequestParam(name = "file") MultipartFile file
    ) throws Exception {
        int ownerId = this.getOwnerIdFromToken();
        JSONObject res1;
        Map<String, Object> resError1;
        Map<String, Object> resError2;
        resError1 = cvService.store(file);
        res1 = new JSONObject(resError1);
        if (resError1.get(MESSAGE).equals(SUCCESS_STORE_FILE)){
            ApplicantFormUpdate applicantFormUpdate = new ApplicantFormUpdate();
            applicantFormUpdate.setId(id);
            applicantFormUpdate.setJobId(jobId);
            applicantFormUpdate.setUserId(ownerId);
            applicantFormUpdate.setCvId((int)resError1.get(ID));
            JSONObject res2;
            resError2 = applicantFormService.update(applicantFormUpdate);
            res2 = new JSONObject(resError2);
            if(resError2.get(MESSAGE).equals(SUCCESS_UPDATE_APPLICANT_FORM)){
                return new ResponseEntity<>(res2, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(res2, HttpStatus.NOT_FOUND);
            }
        } else{
            return new ResponseEntity<>(res1, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Operation(summary = "Delete applicant form API", description = "Delete applicant form")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteApplicantForm(
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
        if(applicantFormMapper.selectCompanyIdByJobId(applicantFormMapper.selectJobIdById(id)) != userMapper.selectEmployerAndCompanyIdById(ownerId)){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        resError = applicantFormService.delete(id);
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_APPLICANT_FORM)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Delete applicant forms API", description = "Delete applicant forms")
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteApplicantForms(
            @RequestBody DeleteRequest deleteRequest
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(userMapper.selectRoleById(ownerId).equals("CANDIDATE")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        resError = applicantFormService.deleteChoice(deleteRequest.getDeleteIds());
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_APPLICANT_FORM)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public int getOwnerIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getDetails();
        return user.getId();
    }
}
