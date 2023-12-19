package com.web.recruitment.config;

import com.web.recruitment.persistence.dto.User;
import com.web.recruitment.utils.JwtUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.activation.registries.LogSupport.log;

@Slf4j
@WebFilter("/*")
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final List<String> notFilterUrls = Arrays.asList("/v1/auth/login", "/v1/auth/register", "/v1/auth/confirmRegister", "/v1/auth/activate", "/v1/company/select/{id}", "/v1/company/list", "/v1/job/select/{id}", "/v1/job/list_job_in_company", "/v1/job/list");

    public JwtFilter(JwtUtils jwtUtils) {
//        this.redisTemplate = redisTemplate;
        this.jwtUtils = jwtUtils;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        if(request.getRequestURI().substring(0, 14).equals("/v1/job/select/")){
//            return true;
//        }
//        String a = request.getRequestURI();
//        if(a.startsWith("/v1/job/select/")){
//            return true;
//        }
//        if(a.startsWith("/v1/company/select/")){
//            return true;
//        }
        for (String url : notFilterUrls) {
            if (matches(request.getRequestURI(), url)) {
                return true;
            }
        }
        return false;
    }
    private boolean matches(String requestUri, String pattern) {
        // Replace {id} with a regular expression that matches any value
        String regexPattern = pattern.replaceAll("\\{id\\}", "[^/]+");
        Pattern compiledPattern = Pattern.compile(regexPattern);
        Matcher matcher = compiledPattern.matcher(requestUri);
        return matcher.matches();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // check if request has valid Authorization header
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        // get token from header
        String jwtToken = authorizationHeader.substring(7); // "Bearer " has 7 characters
        if (!StringUtils.hasText(jwtToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        User user = jwtUtils.getUserInfo(jwtToken);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

//        // get token from redis based on email and time
//        var tokenBody = jwtUtils.getBody(jwtToken);
//        String keyInRedis = "session:" +  user.getId() + ":" + tokenBody.get("created_at").toString();
//        Object tokenInRedis = redisTemplate.opsForValue().get(keyInRedis);
//        if (tokenInRedis == null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//
//        // compare token in redis and token in request
//        String tokenInRedisStr = tokenInRedis.toString();
//        if (tokenInRedisStr == null || !tokenInRedisStr.equals(jwtToken)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }
//
//        // get user info from redis
//        Map<Object, Object> userInfo = redisTemplate.opsForHash().entries(user.getEmail());
//
//        // user is deleted
//        if (userInfo.get("deleted") != null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        }

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(user, jwtToken, null);
        authentication.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
