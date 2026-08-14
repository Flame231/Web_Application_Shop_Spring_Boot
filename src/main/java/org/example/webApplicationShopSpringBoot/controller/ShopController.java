package org.example.webApplicationShopSpringBoot.controller;

import lombok.RequiredArgsConstructor; // ИСПРАВЛЕНО
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public String saveOrUpdateUser(@ModelAttribute UserDTO userDTO) {
        userService.saveOrUpdateUser(userDTO);
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
