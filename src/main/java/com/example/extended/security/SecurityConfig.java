package com.example.extended.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder()
                        .encode("user")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder()
                        .encode("admin")).roles("ADMIN");
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests().anyRequest().hasRole("USER")
                .and()
                .httpBasic();

    }
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
