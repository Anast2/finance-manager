package com.itclopedia.cources.jwtSecurity.authentication;

import com.itclopedia.cources.jwtSecurity.JwtTokenUtils;
import com.itclopedia.cources.model.User;
import com.itclopedia.cources.services.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(final UserService userService, final JwtTokenUtils jwtTokenUtils,
                                     AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<String> authenticate(final String username, final String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userService.getUserByName(username);
            if (user == null)
                throw new UsernameNotFoundException("User with username " + username + " not found");
            String token = jwtTokenUtils.createJWToken(username, user.getRoles());
            List<String> response = new ArrayList<>();
            response.add(username);
            response.add(token);
            return response;
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
