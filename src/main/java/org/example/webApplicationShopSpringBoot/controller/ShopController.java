package org.example.webApplicationShopSpringBoot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.UserRegistrationDTO;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ShopController {

    private final UserService userService;

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
    public String showRegistrationPage(Model model) {
        model.addAttribute(new UserRegistrationDTO());
        return "registration";
    }

    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute @Valid UserRegistrationDTO userRegistrationDTO, RedirectAttributes redirectAttributes) {
        userService.saveNewUser(userRegistrationDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Пользователь успешно зарегистрирован!");
        return "redirect:/login";
    }

    @GetMapping("/errorPage")
    public String showAccessDeniedPage(Model model) {
        model.addAttribute("message", "У вас нет прав для доступа к этому разделу сайта!");
        model.addAttribute(adminPath);
        model.addAttribute(clientPath);
        model.addAttribute(operatorPath);
        return "errorPage";
    }
}
