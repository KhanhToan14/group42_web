package com.web.recruitment.api;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.recruitment.payload.request.LoginRequest;
import com.web.recruitment.payload.response.MessageResponse;
import com.web.recruitment.payload.response.UserInfoResponse;
// import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.persistence.mapper.UserMapper;
import com.web.recruitment.securiry.jwt.JwtUtils;
import com.web.recruitment.securiry.services.UserDetailsImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    // @Autowired
    // UserSignup userSignup;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/v1/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    // @PostMapping("/signup")
    // public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest
    // signUpRequest) {
    // if (userMapper.existsByUsername(signUpRequest.getUsername())) {
    // return ResponseEntity.badRequest().body(new MessageResponse("Error: Username
    // is already taken!"));
    // }

    // if (userMapper.existsByEmail(signUpRequest.getEmail())) {
    // return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is
    // already in use!"));
    // }

    // // Create new user's account
    // UserSignup user = new UserSignup();
    // user.setUsername(signUpRequest.getUsername());
    // user.setEmail(signUpRequest.getEmail());
    // user.setPassword(encoder.encode(signUpRequest.getPassword()));

    // // Xác định giá trị enum từ chuỗi role
    // RoleEnum userRole;
    // String strRole = signUpRequest.getRole();

    // if (strRole == null) {
    // userRole = RoleEnum.CANDIDATE; // Nếu không có role, mặc định là CANDIDATE
    // } else {
    // switch (strRole.toLowerCase()) {
    // case "admin":
    // userRole = RoleEnum.ADMIN;
    // break;
    // case "employer":
    // userRole = RoleEnum.EMPLOYER;
    // break;
    // default:
    // userRole = RoleEnum.CANDIDATE;
    // }
    // }

    // user.setRole(userRole.name()); // Lưu tên enum vào thuộc tính role

    // userMapper.insert(user);

    // return ResponseEntity.ok(new MessageResponse("User registered
    // successfully!"));
    // }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
