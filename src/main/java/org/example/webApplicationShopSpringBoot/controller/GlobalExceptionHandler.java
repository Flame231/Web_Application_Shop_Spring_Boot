package org.example.webApplicationShopSpringBoot.controller;

import org.example.webApplicationShopSpringBoot.service.exceptions.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler({DifferentPasswordsRegistration.class,
            DifferentPasswordsUpdate.class, EmptyList.class, UserRegistrationException.class,
            WrongPassword.class, WrongLoginOrPassword.class, ResourceNotFound.class})
    public String handleBusinessError(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("adminPath", adminPath);
        model.addAttribute("clientPath", clientPath);
        model.addAttribute("operatorPath", operatorPath);
        return "errorPage";
    }

    @ExceptionHandler(BindException.class)
    public String ValidationHandler(BindException e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("adminPath", adminPath);
        model.addAttribute("clientPath", clientPath);
        model.addAttribute("operatorPath", operatorPath);
        List<String> s = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        model.addAttribute("message", s);
        return "validationErrorPage";
    }
}
