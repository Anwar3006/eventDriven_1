package com.eventdriven.cms.security.session;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.eventdriven.cms.domain.AppUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Configuration
public class JwtGenerator {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());

    public String generateToken(Authentication authObj){
        Collection<? extends GrantedAuthority> authList = authObj.getAuthorities();

        // Convert the list of authorities to a comma-separated string
        String roles = authList.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        AppUser principal = (AppUser) authObj.getPrincipal();

        // Build the jwt
        Long nowMillis = System.currentTimeMillis();
        Long expiryMillis = nowMillis + JwtConstant.JWT_EXPIRATION;
        Date currentTime = new Date(nowMillis);
        Date expiryTime = new Date(expiryMillis);

        String jwt = Jwts.builder()
                        .expiration(expiryTime)
                        .issuedAt(currentTime)
                        .claim("email", principal.getEmail()).claim("authorities", roles)
                        .signWith(secretKey, Jwts.SIG.HS256)
                        .compact();
        
        return jwt;
    }
}
