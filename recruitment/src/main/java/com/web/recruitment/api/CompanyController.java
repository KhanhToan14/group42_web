package com.web.recruitment.api;

import com.web.recruitment.api.dto.DeleteRequest;
import com.web.recruitment.api.dto.company.CompanyInsert;
import com.web.recruitment.api.dto.company.CompanyUpdate;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.UserMapper;
import com.web.recruitment.service.CompanyService;
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

import static com.web.recruitment.utils.ConstantMessages.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Company API", description = "API for functions related to company")
@RestController
@Slf4j
@RequestMapping(value = "/v1/company")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    @Autowired
    private final CompanyService companyService;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    public CompanyController(CompanyService companyService, UserMapper userMapper) {
        this.companyService = companyService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Select company API", description = "select company")
    @GetMapping(path = "/select/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> selectCompany(
            @PathVariable("id") int id
    ) throws Exception {
        Map<String, Object> response;
        JSONObject res;
        response = companyService.select(id);
        res = new JSONObject(response);
        if(response.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
    @Operation(summary = "Get list company API", description = "get list company")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListCompany(
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
        responseBody = companyService.listCompany(filter);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Insert company API", description = "Insert company")
    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> insertCompany(
            @RequestBody CompanyInsert companyInsert
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
        resError = companyService.insert(companyInsert);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_INSERT_COMPANY)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Update company API", description = "Update company")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> updateCompany(
            @RequestBody CompanyUpdate companyUpdate
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = this.getOwnerIdFromToken();
        if(userMapper.selectRoleById(ownerId).equals("CANDIDATE")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        if(userMapper.selectEmployerAndCompanyIdById(ownerId) != companyUpdate.getId()){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        resError = companyService.update(companyUpdate);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_UPDATE_COMPANY)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        else if (resError.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Delete company API", description = "Delete company")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteCompany(
            @PathVariable("id") int id
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
        resError = companyService.delete(id);
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_COMPANY)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Delete companies API", description = "Delete companies")
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteCompanies(
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
        resError = companyService.deleteChoice(deleteRequest.getDeleteIds());
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_COMPANY)){
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