package com.web.recruitment.service;

import com.web.recruitment.api.dto.user.UserInsert;
import com.web.recruitment.api.dto.user.UserUpdate;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.CompanyMapper;
import com.web.recruitment.persistence.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ValidationUtils.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Resource
    private final UserMapper userMapper;
    @Resource
    private final CompanyMapper companyMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, CompanyMapper companyMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, Object> select(int id) throws Exception{
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        User user = userMapper.select(id);
        if(user == null) {
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            response.put(USER, user);
            return response;
        }
    }

    @Override
    public Map<String, Object> listUser(Map<String, Object> filter) throws Exception{
        List<User> retList;
        Map<String, Object> reqInfo = new HashMap<>();
        int pageSize = (int) filter.get(PAGE_SIZE);
        int currentPage = (int) filter.get(CURRENT_PAGE);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int limit = pageSize;
        int offset = pageSize * (currentPage - 1);
        filter.put(LIMIT, limit);
        filter.put(OFFSET, offset);
        String sortBy = (String) filter.get(SORT_BY);
        String sortType = (String) filter.get(SORT_TYPE);
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "username";
        } else {
            sortBy = sortBy.trim();
            if (!sortBy.equals("username") && !sortBy.equals("firstName") && !sortBy.equals("lastName") && !sortBy.equals("email")) {
                sortBy = "username";
            }
        }
        if (sortType == null || sortType.isBlank()) {
            sortType = "desc";
        } else {
            sortType = sortType.trim();
            if (!sortType.equals("asc") && !sortType.equals("desc")) {
                sortType = "asc";
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = userMapper.list(filter);
        int total = retList.size();
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> insert(UserInsert userInsert) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        userInsert.setUsername(autoCorrectFormatName(userInsert.getUsername()));
        String username = userInsert.getUsername();
        if(username == null || username.isBlank()){
            subResError.put(USERNAME, USERNAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            username = username.trim();
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put(USERNAME, username);
            if(!validateOnyLowercaseNumber(username)){
                subResError.put(USERNAME, USERNAME_INVALID_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else if(userMapper.selectByUsername(reqMap) != 0){
                subResError.put(USERNAME, USERNAME_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                userInsert.setUsername(username);
            }
        }
        String firstName = userInsert.getFirstName();
        if(firstName == null || firstName.isBlank()){
            subResError.put(FIRST_NAME, FIRST_NAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            firstName = firstName.trim();
            if (!validateOnlyAlphanumericWhiteSpace(firstName)) {
                subResError.put(FIRST_NAME, FIRSTNAME_INVALID_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                userInsert.setFirstName(firstName);
            }
        }
        String lastName = userInsert.getFirstName();
        if(lastName == null || lastName.isBlank()){
            subResError.put(LAST_NAME, LAST_NAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            lastName = lastName.trim();
            if (!validateOnlyAlphanumericWhiteSpace(lastName)) {
                subResError.put(LAST_NAME, LASTNAME_INVALID_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                userInsert.setLastName(lastName);
            }
        }
        String password = userInsert.getPassword();
        if (password == null || password.isBlank()) {
            subResError.put(PASSWORD, PASSWORD_NOT_NULL_ERROR);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            password = password.trim();
            if (!validatePassword(password)) {
                subResError.put(PASSWORD, PASSWORD_INVALID_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                userInsert.setPassword(passwordEncoder.encode(password));
            }
        }

        String dateOfBirth = userInsert.getDateOfBirth();
        if (dateOfBirth == null || dateOfBirth.isBlank()) {
            subResError.put(DATE_OF_BIRTH, DATE_OF_BIRTH_NOT_NULL_ERROR);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            dateOfBirth = dateOfBirth.trim();
            try {
                LocalDate dob = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate now = LocalDate.now();
                if (ChronoUnit.DAYS.between(dob, now) <= 0) {
                    subResError.put(DATE_OF_BIRTH, DATE_OF_BIRTH_MUST_BEFORE_NOW_ERROR);
                    resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                    resError.put(ERRORS, subResError);
                    return resError;
                } else {
                    userInsert.setDateOfBirth(dateOfBirth);
                }
            } catch (DateTimeParseException ex) {
                subResError.put(DATE_OF_BIRTH, DATE_OF_BIRTH_INVALID_FORMAT_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String phone = userInsert.getPhone();
        if(phone == null || phone.isBlank()){
            subResError.put(PHONE, PHONE_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            userInsert.setPhone(validateVietnamesePhoneNumber(phone));
            if(userInsert.getPhone() == null){
                subResError.put(PHONE, PHONE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String email = userInsert.getEmail();
        if(email == null || email.isBlank()){
            subResError.put(EMAIL, EMAIL_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            email = email.trim();
            Map<String, Object> map = new HashMap<>();
            map.put(EMAIL, email);
            if(!validateEmail(email)){
                subResError.put(EMAIL, EMAIL_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else if (userMapper.selectUserByEmail(map) != 0){
                subResError.put(EMAIL, EMAIL_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                userInsert.setEmail(email);
            }
        }
        userMapper.insert(userInsert);
        resError.put(MESSAGE, SUCCESS_INSERT_USER);
        return resError;
    }

    @Override
    public Map<String, Object> update(UserUpdate userUpdate) throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> delete(int id) throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> deleteChoice(List<Integer> ids) throws Exception{
        return null;
    }

    @Override
    public Map<String, Object> listUserRole(Map<String, Object> filter) throws Exception{
        List<User> retList;
        Map<String, Object> reqInfo = new HashMap<>();
        int pageSize = (int) filter.get(PAGE_SIZE);
        int currentPage = (int) filter.get(CURRENT_PAGE);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize <= 0) {
            pageSize = 30;
        }
        int limit = pageSize;
        int offset = pageSize * (currentPage - 1);
        filter.put(LIMIT, limit);
        filter.put(OFFSET, offset);
        String sortBy = (String) filter.get(SORT_BY);
        String sortType = (String) filter.get(SORT_TYPE);

        if (sortType == null || sortType.isBlank()) {
            sortType = "desc";
        } else {
            sortType = sortType.trim();
            if (!sortType.equals("asc") && !sortType.equals("desc")) {
                sortType = "asc";
            }
        }
        filter.replace(SORT_BY, sortBy);
        filter.replace(SORT_TYPE, sortType);
        retList = userMapper.listRole(filter);
        int total = retList.size();
        reqInfo.put(CURRENT_PAGE, currentPage);
        reqInfo.put(PAGE_SIZE, pageSize);
        reqInfo.put(DATA, retList);
        reqInfo.put(TOTAL, total);
        return reqInfo;
    }
}
