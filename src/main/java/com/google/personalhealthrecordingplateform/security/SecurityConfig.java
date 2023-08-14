package com.google.personalhealthrecordingplateform.security;

import com.google.personalhealthrecordingplateform.security.content.SecurityContent;
import com.google.personalhealthrecordingplateform.security.handler.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers(SecurityContent.WHITE_LIST);
        //ignoring()返回值：WebSecurity.IgnoredRequestConfigurer ignoredRequestRegistry
        //ignoredRequestRegistry.mvcMatchers() 返回值：MvcMatchersIgnoredRequestConfigurer对象
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO 为什么要把解决跨域攻击的功能关掉
        http.csrf().disable();
        //TODO 禁用Session又是啥？SpringSecurity默认是提供了session功能吗？
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //TODO 不理解
        //难道login都不能直接访问吗？
        http.authorizeRequests()
                .anyRequest().authenticated();
        //TODO 不理解
        http.headers().cacheControl();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
