package org.example.webApplicationShopSpringBoot.service;

import org.example.webApplicationShopSpringBoot.model.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class PrincipalProvider {
    public static User getUserFromSecurityContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
