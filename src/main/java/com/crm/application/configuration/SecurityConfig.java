package com.crm.application.configuration;

import com.crm.application.utilModels.auth.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenUtil tokenUtil;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, TokenUtil tokenUtil) {
        this.userDetailsService = userDetailsService;
        this.tokenUtil = tokenUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().and()
                .anonymous().and()
                .csrf().disable()
                .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(new VerifyTokenFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new GenerateTokenForUserFilter("/session", authenticationManager(), tokenUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/home", "/session", "/login/**", "/reset-password/**", "/forgot-password/**", "/logout/**")
                .permitAll()
                .antMatchers("/users", "/client/delete/*")
                .hasAnyAuthority("ADMIN", "MANAGER")
                .antMatchers("/users/**")
                .hasAuthority("ADMIN")
                .antMatchers("/groups/**", "/update/**", "/statistics/**", "/event/**", "/events/**",
                        "/user/**", "/activity/**", "/export/**", "/documents/**", "/contact/**",
                        "/address/**", "/contractor/**", "/clients/**", "/client/edit/*", "/client/*")
                .hasAnyAuthority("ADMIN", "MANAGER", "SALESMAN")
                .anyRequest().authenticated();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }


}