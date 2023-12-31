package com.web.recruitment.service;

import com.web.recruitment.api.dto.user.UserUpdate;
import com.web.recruitment.persistence.dto.ApplicantForm;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.ApplicantFormMapper;
import com.web.recruitment.persistence.mapper.CompanyMapper;
import com.web.recruitment.persistence.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ValidationUtils.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final CompanyMapper companyMapper;
    @Autowired
    private final ApplicantFormMapper applicantFormMapper;
    @Autowired
    private final ApplicantFormService applicantFormService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, CompanyMapper companyMapper, ApplicantFormMapper applicantFormMapper, ApplicantFormService applicantFormService, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
        this.applicantFormMapper = applicantFormMapper;
        this.applicantFormService = applicantFormService;
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
    public Map<String, Object> insert(User user) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        user.setUsername(autoCorrectFormatName(user.getUsername()));
        String username = user.getUsername();
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
            } else if(userMapper.selectCountByUsername(reqMap) != 0){
                subResError.put(USERNAME, USERNAME_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                user.setUsername(username);
            }
        }
        String firstName = user.getFirstName();
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
                user.setFirstName(firstName);
            }
        }
        String lastName = user.getLastName();
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
                user.setLastName(lastName);
            }
        }
        String password = user.getPassword();
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
                user.setPassword(passwordEncoder.encode(password));
            }
        }

        String dateOfBirth = user.getDateOfBirth();
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
                    user.setDateOfBirth(dateOfBirth);
                }
            } catch (DateTimeParseException ex) {
                subResError.put(DATE_OF_BIRTH, DATE_OF_BIRTH_INVALID_FORMAT_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String phone = user.getPhone();
        if(phone == null || phone.isBlank()){
            subResError.put(PHONE, PHONE_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            user.setPhone(validateVietnamesePhoneNumber(phone));
            if(user.getPhone() == null){
                subResError.put(PHONE, PHONE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String email = user.getEmail();
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
            } else if (userMapper.selectCountUserByEmail(map) != 0){
                subResError.put(EMAIL, EMAIL_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                user.setEmail(email);
            }
        }
        userMapper.insert(user);
        resError.put(MESSAGE, SUCCESS_INSERT_USER);
        return resError;
    }

    @Override
    public Map<String, Object> update(UserUpdate userUpdate) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(userUpdate.getId() == null){
            subResError.put(ID, ID_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(userMapper.select(userUpdate.getId()) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        userUpdate.setUsername(autoCorrectFormatName(userUpdate.getUsername()));
        String username = userUpdate.getUsername();
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
            } else if(userMapper.selectCountByUsername(reqMap) != 0){
                subResError.put(USERNAME, USERNAME_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                userUpdate.setUsername(username);
            }
        }
        String firstName = userUpdate.getFirstName();
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
                userUpdate.setFirstName(firstName);
            }
        }
        String lastName = userUpdate.getFirstName();
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
                userUpdate.setLastName(lastName);
            }
        }
        String password = userUpdate.getPassword();
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
                userUpdate.setPassword(passwordEncoder.encode(password));
            }
        }

        String dateOfBirth = userUpdate.getDateOfBirth();
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
                    userUpdate.setDateOfBirth(dateOfBirth);
                }
            } catch (DateTimeParseException ex) {
                subResError.put(DATE_OF_BIRTH, DATE_OF_BIRTH_INVALID_FORMAT_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String phone = userUpdate.getPhone();
        if(phone == null || phone.isBlank()){
            subResError.put(PHONE, PHONE_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            userUpdate.setPhone(validateVietnamesePhoneNumber(phone));
            if(userUpdate.getPhone() == null){
                subResError.put(PHONE, PHONE_INVALID);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }
        String email = userUpdate.getEmail();
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
            } else if (userMapper.selectCountUserByEmail(map) != 0){
                subResError.put(EMAIL, EMAIL_EXIST);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                userUpdate.setEmail(email);
            }
        }
        userMapper.update(userUpdate);
        resError.put(MESSAGE, SUCCESS_UPDATE_USER);
        return resError;
    }

    @Override
    public Map<String, Object> delete(int id) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if(userMapper.select(id) == null){
            subResError.put(ID, ID_NOT_EXIST_ERROR);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        userMapper.delete(id);
        resError.put(MESSAGE, SUCCESS_DELETE_USER);
        List<ApplicantForm> listApplicantForm = applicantFormMapper.listApplicantFormByUserId(id);
        if(listApplicantForm.size() > 0){
            for(ApplicantForm applicantForm : listApplicantForm){
                applicantFormService.delete(applicantForm.getId());
            }
        }
        return resError;
    }

    @Override
    public Map<String, Object> deleteChoice(List<Integer> ids) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        ArrayList<Integer> idsDeleteSuccess = new ArrayList<>();
        ArrayList<Integer> idsDeleteNotSuccess = new ArrayList<>();
        int validId;
        int idsSize = ids.size();
        if(idsSize > 0){
            for(Integer id : ids){
                if(userMapper.select(id) == null){
                    idsDeleteNotSuccess.add(id);
                } else {
                    validId = id;
                    idsDeleteSuccess.add(validId);
                    userMapper.delete(id);
                }
            }
        } else {
            subResError.put(DELETE_IDS, DELETE_IDS_MUST_NOT_NULL_OR_EMPTY);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(idsDeleteSuccess.size() > 0){
            if (idsDeleteNotSuccess.size() > 0){
                resError.put(LIST_ID_CAN_NOT_DELETE, idsDeleteNotSuccess);
                resError.put(LIST_ID_DELETED, idsDeleteSuccess);
                resError.put(MESSAGE, SUCCESS_DELETE_USER);
            } else{
                resError.put(MESSAGE, SUCCESS_DELETE_USER);
            }
            for(int idUser : idsDeleteSuccess){
                List<ApplicantForm> listApplicantForm = applicantFormMapper.listApplicantFormByUserId(idUser);
                if(listApplicantForm.size() != 0){
                    for(ApplicantForm applicantForm : listApplicantForm){
                        applicantFormService.delete(applicantForm.getId());
                    }
                }
            }
        } else {
            subResError.put(DELETE_IDS, IDS_INVALID);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
        }
        return resError;
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
    @Override
    public Map<String, Object> changePassword(int userId, String currentPassword, String newPassword, String confirmNewPassword) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        User user = userMapper.select(userId);
        if(user == null){
            subResError.put(ID, ID_MUST_NOT_NULL);
            resError.put(MESSAGE, NOT_FOUND_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if (currentPassword == null || currentPassword.isBlank()) {
            subResError.put(CURRENT_PASSWORD, CURRENT_PASSWORD_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            currentPassword = currentPassword.trim();
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                subResError.put(CURRENT_PASSWORD, CURRENT_PASSWORD_INCORRECT);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }

        // validate new password
        if (newPassword == null || newPassword.isBlank()) {
            subResError.put(NEW_PASSWORD, NEW_PASSWORD_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            newPassword = newPassword.trim();
            if (newPassword.equals(currentPassword)) {
                subResError.put(NEW_PASSWORD, NEW_PASSWORD_MUST_NOT_SAME_CURRENT_PASSWORD);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else if (!validatePassword(newPassword)) {
                subResError.put(NEW_PASSWORD, PASSWORD_INVALID_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }

        // validate confirm password
        if (confirmNewPassword == null || confirmNewPassword.isBlank()) {
            subResError.put(CONFIRM_NEW_PASSWORD, CONFIRM_NEW_PASSWORD_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            confirmNewPassword = confirmNewPassword.trim();
            if (!confirmNewPassword.equals(newPassword)) {
                subResError.put(CONFIRM_NEW_PASSWORD, CONFIRM_AND_NEW_PASSWORD_NOT_SAME);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            }
        }

        Map<String, String> changePasswordReq = new HashMap<>();
        changePasswordReq.put(ID, Integer.toString(userId));
        changePasswordReq.put(NEW_PASSWORD, passwordEncoder.encode(newPassword));
        int rowsAffecting = userMapper.changePassword(changePasswordReq);
        resError.put(MESSAGE, SUCCESS_CHANGE_PASSWORD);
        return resError;
    }
    @Override
    public User selectByEmail(String email){
        return userMapper.selectByEmail(email);
    }

    @Override
    public User selectByUsername(String username){
        return userMapper.selectByUsername(username);
    }
}
