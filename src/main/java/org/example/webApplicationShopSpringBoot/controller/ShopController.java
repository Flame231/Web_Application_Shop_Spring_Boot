package org.example.webApplicationShopSpringBoot.controller;

import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@Controller
public class ShopController {

    private ProductService productService;
    private UserService userService;

    public ShopController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String getList(Model model) {
        List<ProductDTO> listDTO = productService.getAllProducts(3);
        model.addAttribute("listDTO",listDTO);
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }



    @PostMapping("/saveOrUpdateUser")
    public String saveOrUpdateUser(@ModelAttribute UserDTO userDTO){
        userService.saveOrUpdateUser(userDTO);
        return "login";
    }

}
