package com.web.recruitment.api;

import com.web.recruitment.api.dto.Enum.JobEnum.CurrencyEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.EmploymentTypeEnum;
import com.web.recruitment.api.dto.Enum.JobEnum.ExperienceEnum;
import com.web.recruitment.service.UserService;
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

@Tag(name = "User API", description = "API for functions related to user")
@RestController
@Slf4j
@RequestMapping(value = "/v1/user")
public class UserController {
    @Resource
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(summary = "Select user API", description = "select user")
    @GetMapping(path = "/select/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> selectUser(
            @PathVariable("id") int id
    ) throws Exception {
        Map<String, Object> response;
        JSONObject res;
        response = userService.select(id);
        res = new JSONObject(response);
        if(response.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get list user API", description = "get list user")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListDepartment(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
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
        responseBody = userService.listUser(filter);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
