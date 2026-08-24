package org.example.webApplicationShopSpringBoot.controller.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.core.Authentication;

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.stereotype.Component;

import java.net.http.HttpRequest;

@Component
@Slf4j
public class SecurityLogger {

    @EventListener
    public void success(AuthenticationSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        log.info("Пользователь \"{}\" с ролью {} успешно вошёл с систему!", user.getLogin(), user.getRole().name());
    }

    @EventListener
    public void badCredentials(AuthenticationFailureBadCredentialsEvent event) {
        String userName = event.getAuthentication().getName();
        log.warn("Ошибка входа для логина: {}, введён неверный логин или пароль! ", userName);
    }

    @EventListener
    public void logout(LogoutSuccessEvent event) {
        String userName = event.getAuthentication().getName();
        log.warn("Пользователь {} вышел из системы ", userName);
    }

    @EventListener
    public void accessDenied(AuthorizationDeniedEvent<?> event) {
        Authentication auth = event.getAuthentication().get();
        String userName = event.getAuthentication().get().getName();
        Object secureObject = event.getObject();
        String url = secureObject.toString();
        if (secureObject instanceof HttpServletRequest) {
            url = ((HttpServletRequest) secureObject).getRequestURL().toString();
        }
        log.warn("Попытка несанкционированного доступа: пользователь {} пытался получить к ресурсу {}, в доступе отказано ",
                userName, url);
    }
}
