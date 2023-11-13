package com.web.recruitment.api;

import com.web.recruitment.api.dto.DeleteRequest;
import com.web.recruitment.api.dto.department.DepartmentInsert;
import com.web.recruitment.api.dto.department.DepartmentUpdate;
import com.web.recruitment.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.web.recruitment.utils.ConstantMessages.*;
import java.util.Map;

@Tag(name = "Department API", description = "API for functions related to department")
@RestController
@Slf4j
@RequestMapping(value = "/v1/department")
public class DepartmentController {
    @Resource
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Select department API", description = "select department")
    @GetMapping(path = "/select/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> selectDepartment(
            @PathVariable("id") int id
    ) throws Exception {
        Map<String, Object> response;
        JSONObject res;
        response = departmentService.select(id);
        res = new JSONObject(response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Get list department API", description = "get list department")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListDepartment(
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "sortBy", required = false, defaultValue = "createAt") String sortBy,
            @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType
    ) throws Exception{
        int pageSizeInt;
        try {
            pageSizeInt = Integer.parseInt(pageSize);
        } catch (NumberFormatException ex) {
            pageSizeInt = 30;
        }
        int currentPageInt;
        try {
            currentPageInt = Integer.parseInt(currentPage);
        } catch (NumberFormatException ex) {
            currentPageInt = 1;
        }
        JSONObject res;
        Map<String, Object> responseBody;
        responseBody = departmentService.listDepartment(pageSizeInt, currentPageInt, keyword, sortBy, sortType);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Insert department API", description = "Insert department")
    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertDepartment(
            @RequestBody DepartmentInsert departmentInsert
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = departmentService.insert(departmentInsert);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_INSERT_DEPARTMENT)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Update department API", description = "Update department")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateDepartment(
            @RequestBody DepartmentUpdate departmentUpdate
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = departmentService.update(departmentUpdate);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_UPDATE_DEPARTMENT)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Delete department API", description = "Delete department")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteDepartment(
            @PathVariable("id") int id
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = departmentService.delete(id);
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_DEPARTMENT)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @Operation(summary = "Delete departments API", description = "Delete departments")
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteDepartments(
            @RequestBody DeleteRequest deleteRequest
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = departmentService.deleteChoice(deleteRequest.getDeleteIds());
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_DEPARTMENT)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
