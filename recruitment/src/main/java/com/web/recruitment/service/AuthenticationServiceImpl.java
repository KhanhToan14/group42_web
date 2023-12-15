package com.web.recruitment.service;

import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.UserMapper;
import com.web.recruitment.utils.ImageUtils;
import com.web.recruitment.utils.JwtUtils;
import com.web.recruitment.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static com.web.recruitment.utils.ConstantMessages.*;
import static com.web.recruitment.utils.ValidationUtils.*;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthenticationServiceImpl(UserMapper userMapper, UserService userService, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userMapper = userMapper;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }
    @Value("${user_login.user_locked_time_minute}")
    private long userLockedTimeMinute;

    @Value("${user_login.login_failed_time}")
    private int loginFailedTime;

    @Value(("${user_login.max_login_failed}"))
    private int maxLoginFailed;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> register(User user) throws Exception{
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
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
        String logo = user.getAvatar();
        if (logo == null || logo.isBlank()) {
            user.setAvatar(null);
        } else {
            // validate avatar
            if (!ValidationUtils.validateImageBase64(logo)){
                subResError.put(LOGO, LOGO_INVALID_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else if (!ValidationUtils.validateImageSize(logo)){
                subResError.put(LOGO, LOGO_INVALID_SIZE_ERROR);
                resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
                resError.put(ERRORS, subResError);
                return resError;
            } else {
                try {
                    user.setAvatar(ImageUtils.resize(logo));
                } catch (IOException ex) {
                    resError.put(ERRORS, SOME_THING_WENT_WRONG_MESSAGE);
                }
            }
        }
        User inactivateUser = userMapper.selectInactivateByEmail(user.getEmail());
        if(inactivateUser != null){
            subResError.put(EMAIL, USER_EMAIL_REGISTERED_INACTIVE_ERROR);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        String localPart = user.getEmail().substring(0, user.getEmail().indexOf("@"));
        user.setUsername(localPart);
        String otp = this.generateOtp();
        user.setOtp(otp);
        user.setOtpTimeSent((LocalDateTime.now().plusSeconds(10).toString()));
        userMapper.register(user);

        resError.put(OTP, otp);
        return resError;
    }
    private String generateOtp() {
        StringBuilder otp = new StringBuilder(8);
        for (int i = 0; i < 8; ++i) {
            otp.append(RandomUtils.nextInt(0, 10));
        }
        return otp.toString();
    }

    @Override
    public Map<String, Object> login(String principal, String password) throws Exception{
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();

        // invalid input: email or password null/empty
        if (password == null || StringUtils.isBlank(password)) {
            subResError.put(PASSWORD, PASSWORD_NOT_NULL_ERROR);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            password = password.trim();
        }

        if (principal == null || StringUtils.isBlank(principal)) {
            subResError.put(USERNAME, USERNAME_MUST_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            principal = principal.trim();
        }
        User user;
        if (principal.contains("@")) {
            user = userMapper.selectByEmail(principal);
        } else {
            user = userService.selectByUsername(principal);
        }
        if(user == null){
            subResError.put(LOGIN_NAME, LOGIN_NAME_INVALID);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
//        boolean isLoginSuccess = true;
//        boolean isUserLocked = false;

//        if (user == null) {
//            isLoginSuccess = false;
//        } else if (user.getLockedTime() != null) { // check if user is locked
//            LocalDateTime lockedTime = LocalDateTime.parse(user.getLockedTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            LocalDateTime now = LocalDateTime.now();
//            // if user is still locked
//            if (Duration.between(lockedTime, now).toMinutes() < userLockedTimeMinute) {
//                user.setLockedTime(Duration.between(now, lockedTime.plusMinutes(this.userLockedTimeMinute)).toString());
//                isLoginSuccess = false;
//                isUserLocked = true;
//            }
//            // check password
//        } else if (!passwordEncoder.matches(password, user.getPassword())) {
//            isLoginSuccess = false;
//            if (user.getOtpFailedCount() == maxLoginFailed) {
//                user.setLockedTime(LocalDateTime.now().toString());
//                // locked user to database
//                Map<String, Object> lockUserReq = Map.of(ID, user.getId(), LOCKED_TIME, user.getLockedTime());
//                userMapper.lockUser(lockUserReq);
//
//                user.setLockedTime(Duration.ofMinutes(this.userLockedTimeMinute).toString());
//                isUserLocked = true;
//            } else {
//                // increase login failed count
//                userMapper.increaseLoginFailedCount(user.getId());
//            }
//        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            subResError.put(PASSWORD, PASSWORD_INCORRECT);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
//        if (!isLoginSuccess) {
//            res.put(USER, user);
//            res.put(SUCCESS, false);
//            if (isUserLocked) {
//                res.put(LOCKED, true);
//            }
//            return res;
//        }

        // unlock user
//        if (user.getLockedTime() != null) {
//            Map<String, Object> unlockReq = new HashMap<>();
//            unlockReq.put(ID, user.getId());
//            userMapper.lockUser(unlockReq);
//        }


//        user.setLockedTime(null);
        res.put(SUCCESS, true);
        res.put(USER, user);
        return res;
    }

    @Override
    public String handleLoginSuccess(User user){
        String jwtToken = jwtUtils.generateToken(user);
        if (jwtToken == null) {
            throw new InternalServerErrorException(SOME_THING_WENT_WRONG_MESSAGE);
        }
        return jwtToken;
    }

    @Override
    public Map<String, Object> confirmRegister(String email, String otp){
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if (email == null || email.isBlank()) {
            subResError.put(EMAIL, EMAIL_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            email = email.trim();
        }
        if (otp == null || otp.isBlank()) {
            subResError.put(OTP, OTP_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        } else {
            otp = otp.trim();
        }
        User existingUser = userMapper.selectByEmail(email);
        if (existingUser != null) {
            subResError.put(EMAIL, EMAIL_EXIST);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        User inactiveUser = userMapper.selectInactivateByEmail(email);
        if (inactiveUser == null) {
            subResError.put(EMAIL, EMAIL_NOT_REGISTERED);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        if(inactiveUser.getOtp() == null){
            subResError.put(OTP, OTP_DEACTIVATED + "_" + USER_REQUEST_NEW_OTP);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        LocalDateTime otpTimeSent = LocalDateTime.parse(inactiveUser.getOtpTimeSent(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (Duration.between(otpTimeSent, LocalDateTime.now()).toSeconds() > 12000) {
            subResError.put(OTP, OTP_EXPIRED + "_" + USER_REQUEST_NEW_OTP);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        userMapper.activate(inactiveUser.getId());
        resError.put(SUCCESS, true);
        return resError;
    }
    @Override
    public Map<String, Object> activateUser(String email){
        Map<String, Object> subResError = new HashMap<>();
        Map<String, Object> resError = new HashMap<>();
        if (email == null || email.isBlank()) {
            subResError.put(EMAIL, EMAIL_NOT_NULL);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }

        email = email.trim();

        if (userMapper.selectByEmail(email) != null) {
            subResError.put(EMAIL, EMAIL_EXIST);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }

        User inactivateUser = userMapper.selectInactivateByEmail(email);
        if (inactivateUser == null) {
            subResError.put(EMAIL, EMAIL_NOT_REGISTERED);
            resError.put(MESSAGE, INVALID_INPUT_MESSAGE);
            resError.put(ERRORS, subResError);
            return resError;
        }
        boolean shouldNotGenerateNewOtp = true;
        if (inactivateUser.getOtp() == null) {
            shouldNotGenerateNewOtp = false;
        } else {
            LocalDateTime otpTimeSent = LocalDateTime.parse(inactivateUser.getOtpTimeSent(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (Duration.between(otpTimeSent, LocalDateTime.now()).toSeconds() > 60) {
                shouldNotGenerateNewOtp = false;
            }
        }

        if (shouldNotGenerateNewOtp) {
            resError.put(MESSAGE, OTP_ALREADY_SENT_MESSAGE);
            return resError;
        }

        String newOtp = this.generateOtp();

        Map<String, String> setNewOtpReq = new HashMap<>();
        setNewOtpReq.put(ID, Long.toString(inactivateUser.getId()));
        setNewOtpReq.put(OTP, newOtp);
        setNewOtpReq.put(OTP_TIME_SENT, LocalDateTime.now().plusSeconds(10).toString());
        if (userMapper.setNewOtp(setNewOtpReq) != 1) {
            resError.put(MESSAGE, SOME_THING_WENT_WRONG_MESSAGE);
            return resError;
        }

        resError.put(MESSAGE, NEW_OTP_GENERATED_MESSAGE);
        resError.put(OTP, newOtp);

        return resError;
    }
}
