package com.web.recruitment.service;

import com.web.recruitment.api.dto.user.UserInsert;
import com.web.recruitment.persistence.dto.User;

import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> register(User user) throws Exception;

    Map<String, Object> login(String principal, String password) throws Exception;

    String handleLoginSuccess(User user);

    Map<String, Object> confirmRegister(String email, String otp);

    Map<String, Object> activateUser(String email);
}
