package com.web.recruitment.api;

import com.web.recruitment.api.dto.department.DepartmentInsert;
import com.web.recruitment.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, Object> selectDepartment(
            @PathVariable("id") int id
    ) throws Exception {
        Map<String, Object> response;
        response = departmentService.select(id);
        return response;
    }
    @Operation(summary = "Get list department API", description = "get list department")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getListDepartment(
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
        Map<String, Object> responseBody;
        responseBody = departmentService.listDepartment(pageSizeInt, currentPageInt, keyword, sortBy, sortType);
        return responseBody;
    }

    @Operation(summary = "Insert department API", description = "Insert department")
    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> insertDepartment(
            @RequestBody() DepartmentInsert departmentInsert
    )
    throws Exception{
        Map<String, Object> response;
        response = departmentService.insert(departmentInsert);
        return response;
    }

}
