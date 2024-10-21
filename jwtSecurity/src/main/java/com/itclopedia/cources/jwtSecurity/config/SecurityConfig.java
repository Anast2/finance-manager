package com.itclopedia.cources.jwtSecurity.config;

import com.itclopedia.cources.jwtSecurity.JwtTokenFilter;
import com.itclopedia.cources.jwtSecurity.JwtTokenUtils;
import com.itclopedia.cources.jwtSecurity.userDetails.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String LOGIN_ENDPOINT = "/v1/auth/login";
    private static final String REGISTER_ENDPOINT = "/v1/users/register";
    private static final String ADMIN_ENDPOINT = "/v1/users/all";
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public SecurityConfig(JwtTokenUtils jwtTokenUtils, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(LOGIN_ENDPOINT, REGISTER_ENDPOINT).permitAll()
                        .requestMatchers(ADMIN_ENDPOINT).hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(jwtTokenUtils),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(jwtUserDetailsService);
        return daoAuthenticationProvider;
    }

}
