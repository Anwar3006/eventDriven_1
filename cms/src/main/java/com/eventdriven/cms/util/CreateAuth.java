package com.eventdriven.cms.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.eventdriven.cms.domain.AppUser;

@Component
public class CreateAuth {

    public Authentication createAuthObj(AppUser user){
        Authentication authObj = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authObj);
        return authObj;
    }
}
