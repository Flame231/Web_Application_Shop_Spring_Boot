package org.example.webApplicationShopSpringBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.service.exceptions.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${adminPath}")
    private String adminPath;

    @Value("${clientPath}")
    private String clientPath;

    @Value("${operatorPath}")
    private String operatorPath;

    @ModelAttribute("adminPath")
    public String getAdminPath() {
        return adminPath;
    }

    @ModelAttribute("clientPath")
    public String getClientPath() {
        return clientPath;
    }

    @ModelAttribute("operatorPath")
    public String getOperatorPath() {
        return operatorPath;
    }

    @ExceptionHandler({DifferentUserPasswords.class, EmptyList.class, UserRegistrationException.class,
            WrongPassword.class, ResourceNotFound.class})
    public String handleBusinessError(Exception e, Model model) {
        log.warn("Ошибка сервиса: {}", e.getMessage());
        model.addAttribute("message", e.getMessage());
        model.addAttribute(adminPath);
        model.addAttribute(clientPath);
        model.addAttribute(operatorPath);
        return "errorPage";
    }

    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public String validationHandler(MethodArgumentNotValidException e, Model model) {
        log.warn("Ошибка валидации: {}", e.getMessage());
        model.addAttribute(adminPath);
        model.addAttribute(clientPath);
        model.addAttribute(operatorPath);
        List<String> s = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        model.addAttribute("message", s);
        return "validationErrorPage";
    }
}
