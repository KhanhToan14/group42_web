package com.web.recruitment.config;

import com.web.recruitment.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("Login")
    private AuthenticationProvider loginAuthenticationProvider;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //, "/v1/auth/register", "/v1/auth/confirmRegister", "/v1/auth/activate"
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/v1/auth/login", "/v1/auth/register", "/v1/auth/confirmRegister", "/v1/auth/activate")
                .permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-ui/*", "/api-docs", "/api-docs/swagger-config")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .antMatcher("/v1/**")
                .addFilterAfter(new JwtFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class);
    }
}
