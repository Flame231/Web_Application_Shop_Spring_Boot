package org.example.webApplicationShopSpringBoot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
}
