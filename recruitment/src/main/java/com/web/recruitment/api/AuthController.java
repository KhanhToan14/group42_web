package com.web.recruitment.api;

import com.web.recruitment.payload.request.LoginRequest;
import com.web.recruitment.payload.response.MessageResponse;
import com.web.recruitment.payload.response.UserInfoResponse;
import com.web.recruitment.securiry.jwt.JwtUtils;
import com.web.recruitment.securiry.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authen")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<?> postExample(@Valid @RequestBody LoginRequest loginRequest) {
        // Xử lý dữ liệu nhận được từ yêu cầu POST
        // Ở đây, đơn giản là trả về dữ liệu đã nhận được
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
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
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("đã xóa cookie jwt!"));
    }
}
