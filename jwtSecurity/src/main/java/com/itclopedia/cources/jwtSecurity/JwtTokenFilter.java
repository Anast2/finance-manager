package com.itclopedia.cources.jwtSecurity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private static final String LOGIN_ENDPOINT = "/v1/auth/login";
    private static final String REGISTER_ENDPOINT = "/v1/users/register";
    private final JwtTokenUtils jwtTokenUtils;

    public JwtTokenFilter(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String path = request.getServletPath();
        if (path.equals(LOGIN_ENDPOINT) || path.equals(REGISTER_ENDPOINT)) {
            chain.doFilter(req, res);
            return;
        }
        String token = jwtTokenUtils.resolveToken((HttpServletRequest) req);
        if (token != null && jwtTokenUtils.validateToken(token)) {
            Authentication auth = jwtTokenUtils.getAuthentication(token);
            if (auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req, res);
    }

}
