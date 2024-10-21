package com.itclopedia.cources.controller;

import com.itclopedia.cources.dto.AuthRequest;
import com.itclopedia.cources.dto.AuthResponse;
import com.itclopedia.cources.jwtSecurity.userDetails.JwtUserDetailsService;
import com.itclopedia.cources.jwtSecurity.authentication.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/auth/login")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        log.info("Login request: {}", authRequest);
        List<String> temp = authenticationService.authenticate(authRequest.getUserName(), authRequest.getPassword());
        return new ResponseEntity<>(new AuthResponse(temp.get(0), temp.get(1)), HttpStatus.OK);
    }

}
