package com.trueman.KP_Vacancy.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_MODER;

    @Override
    public String getAuthority() {
        return name();
    }
}
