package org.example.webApplicationShopSpringBoot.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    CLIENT,
    OPERATOR,
    ADMINISTRATOR;


    @Override
    public  String getAuthority() {
        return "name()";
    }
}
