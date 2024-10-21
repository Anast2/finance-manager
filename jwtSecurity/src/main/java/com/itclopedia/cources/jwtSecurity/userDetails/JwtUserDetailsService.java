package com.itclopedia.cources.jwtSecurity.userDetails;

import com.itclopedia.cources.model.User;
import com.itclopedia.cources.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);
    private final UserService userService;

    public JwtUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        log.info("Load by username {} successful", username);
        return JwtUserFactory.create(user);
    }

}
