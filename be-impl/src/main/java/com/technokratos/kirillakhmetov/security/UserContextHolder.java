package com.technokratos.kirillakhmetov.security;

import com.technokratos.kirillakhmetov.entity.Owner;

public interface UserContextHolder {
    Owner getUserFromSecurityContext();

    Long getUserIdFromSecurityContext();
}
