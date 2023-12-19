package com.web.recruitment.api;

import com.web.recruitment.api.dto.DeleteRequest;
import com.web.recruitment.api.dto.Enum.UserEnum.GenderEnum;
import com.web.recruitment.api.dto.Enum.UserEnum.RoleEnum;
import com.web.recruitment.api.dto.user.UserChangePassword;
import com.web.recruitment.api.dto.user.UserUpdate;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.UserMapper;
import com.web.recruitment.service.UserService;
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

import javax.ws.rs.InternalServerErrorException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.web.recruitment.utils.ConstantMessages.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

@Tag(name = "User API", description = "API for functions related to user")
@RestController
@Slf4j
@RequestMapping(value = "/v1/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Operation(summary = "Select user API", description = "select user")
    @GetMapping(path = "/select/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> selectUser(
            @PathVariable("id") int id
    ) throws Exception {
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = Objects.requireNonNull(this.getLogging()).getId();
        if(!userMapper.selectRoleById(ownerId).equals("ADMIN")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        Map<String, Object> response;
        JSONObject res;
        response = userService.select(id);
        res = new JSONObject(response);
        if(response.containsKey(ERRORS)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @Operation(summary = "Get list user API", description = "get list user")
    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> getListUser(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "sortBy", required = false, defaultValue = "username") String sortBy,
            @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = Objects.requireNonNull(this.getLogging()).getId();
        if(!userMapper.selectRoleById(ownerId).equals("ADMIN")){
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

    /*@Operation(summary = "Insert user API", description = "Insert user")
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

        resError = userService.insert(user);
        res = new JSONObject(resError);
        if (resError.get(MESSAGE).equals(SUCCESS_INSERT_USER)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
    }*/

    @Operation(summary = "Update user API", description = "update user")
    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> updateUser(
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
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = Objects.requireNonNull(this.getLogging()).getId();
        if(!userMapper.selectRoleById(ownerId).equals("ADMIN")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
        JSONObject res;
        Map<String, Object> resError;
        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setId(ownerId);
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
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteUser(
            @PathVariable("id") int id
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = Objects.requireNonNull(this.getLogging()).getId();
        if(!userMapper.selectRoleById(ownerId).equals("ADMIN")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
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
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> deleteUsers(
            @RequestBody DeleteRequest deleteRequest
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = Objects.requireNonNull(this.getLogging()).getId();
        if(!userMapper.selectRoleById(ownerId).equals("ADMIN")){
            map.put(MESSAGE, YOU_CAN_NOT_USE_THIS_FUNCTION);
            request = new JSONObject(map);
            return new ResponseEntity<>(request, HttpStatus.FORBIDDEN);
        }
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
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> getListRole(
            @RequestParam(name = "role") RoleEnum role,
            @RequestParam(name = "pageSize", required = false, defaultValue = "30") String pageSize,
            @RequestParam(name = "currentPage", required = false, defaultValue = "1") String currentPage,
            @RequestParam(value = "sortBy", required = false, defaultValue = "username") String sortBy,
            @RequestParam(value = "sortType", required = false, defaultValue = "asc") String sortType
    ) throws Exception{
        Map<String, Object> map = new HashMap<>();
        JSONObject request;
        int ownerId = Objects.requireNonNull(this.getLogging()).getId();
        if(!userMapper.selectRoleById(ownerId).equals("ADMIN")){
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
        filter.put(ROLE, role);

        Map<String, Object> responseBody;
        responseBody = userService.listUser(filter);
        res = new JSONObject(responseBody);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    private User getLogging(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (User) authentication.getDetails();
    }

    @Operation(summary = "Get profile user logging API", description = "select profile user logging ")
    @GetMapping(path = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> profile(
    ) throws Exception {
        Map<String, Object> response;
        JSONObject res;
        int id = Objects.requireNonNull(this.getLogging()).getId();
        response = userService.select(id);
        res = new JSONObject(response);
        if(response.containsKey(ERRORS)){
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
    @Operation(summary = "Change user's password API", description = "Change user's password")
    @PutMapping(path = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<Object> changePassword(@RequestBody UserChangePassword userChangePassword) {
        Map<String, Object> response;
        JSONObject res;
        User user = this.getLogging();
        if (user == null) {
            throw new InternalServerErrorException();
        }

        Map<String, Object> changePasswordResult;
        try {
            changePasswordResult = userService.changePassword(
                    user.getId(),
                    userChangePassword.getCurrentPassword(),
                    userChangePassword.getNewPassword(),
                    userChangePassword.getConfirmNewPassword());
        } catch (Exception ex) {
            throw new InternalServerErrorException();
        }
        res = new JSONObject(changePasswordResult);
        if (changePasswordResult.get(MESSAGE).equals(INVALID_INPUT_MESSAGE)) {
            return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (changePasswordResult.containsKey(ID)) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
