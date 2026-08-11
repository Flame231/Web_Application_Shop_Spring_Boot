package org.example.webApplicationShopSpringBoot.controller.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMINISTRATOR")) {
            response.sendRedirect("/administrator/accountAdministrator");
        } else if (roles.contains("ROLE_CLIENT")) {
            response.sendRedirect("/client/accountClient");
        } else if (roles.contains("ROLE_OPERATOR")) {
            response.sendRedirect("/operator/accountOperator");
        }

    }
}
