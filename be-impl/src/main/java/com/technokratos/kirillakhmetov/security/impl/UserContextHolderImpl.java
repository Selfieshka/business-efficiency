package com.technokratos.kirillakhmetov.security.impl;

import com.technokratos.kirillakhmetov.entity.Owner;
import com.technokratos.kirillakhmetov.security.UserContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserContextHolderImpl implements UserContextHolder {
    @Override
    public Owner getUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No authenticated user found in security context");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetailsImpl userDetails) return userDetails.getOwner();

        throw new UsernameNotFoundException("Unknown principal type: " + principal.getClass().getName());
    }

    @Override
    public Long getUserIdFromSecurityContext() {
        return getUserFromSecurityContext().getId();
    }
}