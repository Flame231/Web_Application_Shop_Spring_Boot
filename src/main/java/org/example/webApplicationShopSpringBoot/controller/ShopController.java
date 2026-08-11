package org.example.webApplicationShopSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor; // ИСПРАВЛЕНО
import org.example.webApplicationShopSpringBoot.dto.dto.UserProfileDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor // ИСПРАВЛЕНО: Генерирует конструктор ТОЛЬКО для final-полей
public class ShopController {

    private final UserService userService;
    private final ErrorPageRegistrar errorPageRegistrar;

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

    @RequestMapping("/login")
    public String getList(Model model) {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/saveOrUpdateUser")
    public String saveNewUser(@ModelAttribute @Valid UserRegistrationDTO userRegistrationDTO) {
        System.out.println(userRegistrationDTO);
        userService.saveNewUser(userRegistrationDTO);
        return "login";
    }

    @GetMapping("/errorPage")
    public String showAccessDeniedPage(Model model) {
        model.addAttribute("message", "У вас нет прав для доступа к этому разделу сайта!");
        model.addAttribute("adminPath", adminPath);
        model.addAttribute("clientPath", clientPath);
        model.addAttribute("operatorPath", operatorPath);
        return "errorPage";
    }
}
