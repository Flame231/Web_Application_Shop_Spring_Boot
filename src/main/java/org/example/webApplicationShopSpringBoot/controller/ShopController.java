package org.example.webApplicationShopSpringBoot.controller;

import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserDTO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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
        Pageable pageable = PageRequest.of(1, 10, Sort.by("id").ascending());
        Page<ProductDTO> listDTO = productService.getAllProducts(pageable);
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
