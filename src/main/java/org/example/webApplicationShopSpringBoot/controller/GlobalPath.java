package org.example.webApplicationShopSpringBoot.controller;

import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalPath {

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

    @ExceptionHandler(EmptyList.class)
    public String handleBusinessError(EmptyList e, Model model) {
        model.addAttribute("message", e.getMessage());
        model.addAttribute("adminPath", adminPath);
        model.addAttribute("clientPath", clientPath);
        model.addAttribute("operatorPath", operatorPath);
        return "errorPage";
    }
}
