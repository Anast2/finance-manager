package com.itclopedia.cources.jwtSecurity;

import com.itclopedia.cources.jwtSecurity.exception.JwtAuthenticationException;
import com.itclopedia.cources.jwtSecurity.userDetails.JwtUserFactory;
import com.itclopedia.cources.model.Role;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.nio.charset.StandardCharsets;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.lifetime}")
    private Duration lifeTime;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtTokenUtils(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createJWToken(String name, Set<Role> roles) {
        Claims claims = Jwts.claims().setSubject(name);
        List<Role> roleList = new ArrayList<>(roles);
        claims.put("roles", getPermissionsNames(roleList));
        Date issueDate = new Date();
        Date expiredDate = new Date(issueDate.getTime() + lifeTime.toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issueDate)
                .setSubject(name)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "");
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", e);
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getPermissionsNames(List<Role> roles) {
        List<GrantedAuthority> authorities = JwtUserFactory.mapToGrantedAuthorities(roles);
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

}
