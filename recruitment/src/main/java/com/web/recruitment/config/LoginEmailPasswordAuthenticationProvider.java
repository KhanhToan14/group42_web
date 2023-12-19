package com.web.recruitment.config;

import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Login")
public class LoginEmailPasswordAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginEmailPasswordAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user;
        if (principal.contains("@")) {
            user = userService.selectByEmail(principal);
        } else {
            user = userService.selectByUsername(principal);
        }

        if (user == null) {
            throw new BadCredentialsException("InvalidCredentials");
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            var auth = new UsernamePasswordAuthenticationToken(principal, password, null);
            auth.setDetails(user);
            return auth;
        }

        throw new BadCredentialsException("InvalidCredentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
