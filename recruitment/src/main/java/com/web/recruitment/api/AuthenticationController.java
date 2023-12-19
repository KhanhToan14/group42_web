package com.web.recruitment.api;

import com.web.recruitment.api.dto.Enum.UserEnum.GenderEnum;
import com.web.recruitment.api.dto.Enum.UserEnum.RoleEnum;
import com.web.recruitment.api.dto.auth.ConfirmRegisterRequest;
import com.web.recruitment.api.dto.auth.LoginRequest;
//import com.web.recruitment.exception.ValidationException;
import com.web.recruitment.persistence.dto.EmailDetails;
import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.service.AuthenticationService;
import com.web.recruitment.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.InternalServerErrorException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static com.web.recruitment.utils.ConstantMessages.*;

@Tag(name = "Authentication API", description = "API for functions related to authentication, such as login, logout")
@RestController
@Slf4j
@RequestMapping(value = "/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private final EmailService emailService;

    public AuthenticationController(AuthenticationService authenticationService, EmailService emailService) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;
    }
    @Operation(summary = "Login API.", description = "Log user in.")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) throws Exception{
        Map<String, Object> loginRes = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
        JSONObject res;
        res = new JSONObject(loginRes);
        if (loginRes.containsKey(ERRORS)) {
            return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        boolean isLoginSuccess = (boolean) loginRes.get(SUCCESS);
//        boolean isLocked = loginRes.containsKey(LOCKED);
        User user = (User) loginRes.get(USER);
//        Integer userId = user != null ? user.getId() : null;

        // login failed & user is not determined
        if (user == null) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

//        // user is locked
//        if (isLocked) {
//            throw new ValidationException(this.handleLockedMessage(user.getLockedTime()), Collections.emptyMap());
//        }

        // login failed & user is determined
        if (!isLoginSuccess) {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        JSONObject res1;
        res1 = new JSONObject(this.handleLoginSuccess(user));
        return new ResponseEntity<>(res1, HttpStatus.OK);
    }

    private Map<String, Object> handleLoginSuccess(User user) {
        String jwtToken;
        String createdAt = user.getCreateAt();
        String tokenTime = LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        user.setCreateAt(tokenTime);
        try {
            jwtToken = authenticationService.handleLoginSuccess(user);
            user.setCreateAt(createdAt);
        } catch (Exception ex) {
            throw new InternalServerErrorException(SOME_THING_WENT_WRONG_MESSAGE);
        }


        return Map.of(
                USER, this.buildReturnedUser(user),
                ACCESS_TOKEN, jwtToken
        );
    }
    private Map<String, String> buildReturnedUser(User user) {
        Map<String, String> returnedUser = new HashMap<>();
        returnedUser.put(ID, Long.toString(user.getId()));
        returnedUser.put(EMAIL, user.getEmail());
        returnedUser.put(AVATAR, user.getAvatar());
        return returnedUser;
    }
//    private String handleLockedMessage(String remainingLockedTime) {
//        // calculate user's waiting time before login again
//        Duration duration = Duration.parse(remainingLockedTime);
//        long timeWaitMinute = duration.toMinutes();
//        long timeWaitSecond = duration.getSeconds() - timeWaitMinute * 60;
//
//        if (timeWaitMinute > 0) {
//            if (timeWaitSecond > 0) {
//                return "YourAccountLockedFor" + timeWaitMinute + "Minutes" + timeWaitSecond + "Seconds";
//            } else {
//                return "YourAccountLockedFor" + timeWaitMinute + "Minutes";
//            }
//        }
//
//        return "YourAccountLockedFor" + timeWaitSecond + "Seconds";
//    }
    @Operation(summary = "Register API.", description = "Register new user.")
    @PostMapping("/register")
    public ResponseEntity<Object> register(
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
    ) throws Exception {
        Map<String, Object> res;

        User user = User.builder()
                .password(password)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(dateOfBirth)
                .avatar(avatar)
                .phone(phone)
                .address(address)
                .gender(gender)
                .role(role)
                .companyId(companyId)
                .build();
        Map<String, Object> registerResult;
        registerResult = authenticationService.register(user);
        res = new JSONObject(registerResult);
        if (registerResult.containsKey(OTP)) {
            String otp = registerResult.get(OTP).toString();
            this.sendOTP(user.getEmail(), otp);
            res.put(MESSAGE, AUTH_SUCCESS_REGISTER_USER);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(res, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    private void sendOTP(String recipient, String otp) throws Exception {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(recipient);
        emailDetails.setBody("Your confirm otp is:"+ otp + ". This code will be valid for 2 minutes. If you have not requested for registering our service, please ignore this mail. Thanks");
        emailDetails.setHtml(true);
        emailDetails.setSubject("Confirm OTP for Recruitment Service");
//        emailTrapService.sendEmail(emailDetails);
        emailService.sendSimpleMail(emailDetails);
    }
    @Operation(summary = "Confirm Register API.", description = "Confirm register new user.")
    @PostMapping("/confirmRegister")
    public Map<String, Object> confirmRegister(@RequestBody ConfirmRegisterRequest confirmRegisterRequest) {

        Map<String, Object> res = new HashMap<>();

        Map<String, Object> confirmRegisterResult =
                authenticationService.confirmRegister(
                        confirmRegisterRequest.getEmail(),
                        confirmRegisterRequest.getOtp());

        if (confirmRegisterResult.containsKey(SUCCESS)) {
            res.put(MESSAGE, AUTH_SUCCESS_ACTIVATE_USER);
            return res;
        }
        return null;
//        throw new ValidationException(INVALID_INPUT_MESSAGE, confirmRegisterResult);
    }
}
