package com.web.recruitment.api;

import com.web.recruitment.api.dto.DeleteRequest;
import com.web.recruitment.api.dto.Enum.UserEnum.GenderEnum;
import com.web.recruitment.api.dto.Enum.UserEnum.RoleEnum;
import com.web.recruitment.api.dto.user.UserInsert;
import com.web.recruitment.api.dto.user.UserUpdate;
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
    public ResponseEntity<Object> getListUser(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "sortBy", required = false, defaultValue = "username") String sortBy,
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

    @Operation(summary = "Insert user API", description = "Insert user")
    @PostMapping(path = "/insert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> insertUser(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "gender") GenderEnum gender,
            @RequestParam(name = "dateOfBirth") String dateOfBirth,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "avatar", required = false) String avatar,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "role") RoleEnum role,
            @RequestParam(name = "companyId", required = false) Integer companyId
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        UserInsert userInsert = new UserInsert();
        userInsert.setUsername(username);
        userInsert.setFirstName(firstName);
        userInsert.setLastName(lastName);
        userInsert.setGender(gender);
        userInsert.setDateOfBirth(dateOfBirth);
        userInsert.setPhone(phone);
        userInsert.setEmail(email);
        userInsert.setAddress(address);
        userInsert.setAvatar(avatar);
        userInsert.setPassword(password);
        userInsert.setRole(role);
        userInsert.setCompanyId(companyId);

        resError = userService.insert(userInsert);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_INSERT_USER)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Update user API", description = "update user")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateUser(
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "gender") GenderEnum gender,
            @RequestParam(name = "dateOfBirth") String dateOfBirth,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "address", required = false) String address,
            @RequestParam(name = "avatar", required = false) String avatar,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "role") RoleEnum role,
            @RequestParam(name = "companyId", required = false) Integer companyId
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setId(id);
        userUpdate.setUsername(username);
        userUpdate.setFirstName(firstName);
        userUpdate.setLastName(lastName);
        userUpdate.setGender(gender);
        userUpdate.setDateOfBirth(dateOfBirth);
        userUpdate.setPhone(phone);
        userUpdate.setEmail(email);
        userUpdate.setAddress(address);
        userUpdate.setAvatar(avatar);
        userUpdate.setPassword(password);
        userUpdate.setRole(role);
        userUpdate.setCompanyId(companyId);

        resError = userService.update(userUpdate);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_UPDATE_USER)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if (resError.get(MESSAGE).equals(NOT_FOUND_MESSAGE)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Delete user API", description = "Delete user")
    @DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(
            @PathVariable("id") int id
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = userService.delete(id);
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_USER)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }
    @Operation(summary = "Delete users API", description = "Delete users")
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUsers(
            @RequestBody DeleteRequest deleteRequest
    ) throws Exception{
        JSONObject res;
        Map<String, Object> resError;
        resError = userService.deleteChoice(deleteRequest.getDeleteIds());
        res = new JSONObject(resError);
        if(resError.get(MESSAGE).equals(SUCCESS_DELETE_USER)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Operation(summary = "Get list role user API", description = "get list role user")
    @GetMapping(path = "/list_role", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getListRole(
            @RequestParam(name = "role") RoleEnum role,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "sortBy", required = false, defaultValue = "username") String sortBy,
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
        filter.put(ROLE, role);

        Map<String, Object> responseBody;
        responseBody = userService.listUser(filter);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
