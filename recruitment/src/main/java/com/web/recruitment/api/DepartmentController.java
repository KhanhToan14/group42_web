package com.web.recruitment.api;

import com.web.recruitment.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @PathVariable("id") Long id
    ) throws Exception {
        Map<String, Object> response;
        response = departmentService.select(id);
        return response;
    }
}
