package com.eventdriven.cms.security.session;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventdriven.cms.services.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Component
public class JwtValidator extends OncePerRequestFilter{
    
    private final Logger logger = LoggerFactory.getLogger(JwtValidator.class);
    private final CustomUserDetailsService userDetailsService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String jwt = request.getHeader("Authorization");
        if(jwt != null && jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);

            try {
                SecretKey secretKey = Keys.hmacShaKeyFor(JwtConstant.JWT_SECRET.getBytes());
                Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();

                String email = String.valueOf(claims.get("email"));
                String roles = String.valueOf(claims.get("authorities"));

                //Build the Authentication object
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                // if(userDetails.isEnabled()){
                //     throw new DisabledException("Account not activated. Please activate your account from the link sent to your email.");
                // }

                List<GrantedAuthority> authList = AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authList);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                logger.error(e.getMessage(), e.getCause());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                return;
            } catch (DisabledException e) {
                logger.error("Account is not enabled: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Account not confirmed. Please confirm your account.");
                return;
            } catch (Exception e) {
                logger.error("Authentication Error: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
