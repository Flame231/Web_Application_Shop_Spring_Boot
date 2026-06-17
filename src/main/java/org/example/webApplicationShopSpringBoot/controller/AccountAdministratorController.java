package org.example.webApplicationShopSpringBoot.controller;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller

public class AccountAdministratorController {

    private ProductCategoryService productCategoryService;
    private ProductService productService;
    private SellerService sellerService;

    public AccountAdministratorController(ProductCategoryService productCategoryService, ProductService productService, SellerService sellerService) {
        this.productCategoryService = productCategoryService;
        this.productService = productService;
        this.sellerService = sellerService;
    }

    @PostMapping("/editProductCategories")
    public String showEditProductCategoriesPage(Model model) {
        List<ProductCategoryDTO> productCategoriesList = productCategoryService.getProductCategoryDTOList();
        model.addAttribute("productCategoriesList", productCategoriesList);
        return "superUser/productCategory/editProductCategories";
    }

    @PostMapping("/accountAdministrator")
    public String showAdministratorPage(Model model) {
        return "account/accountAdministrator";
    }

    @PostMapping("/editCatalog")
    public String showEditCatalogPage(Model model) {
        List<ProductDTO> productDTOList = productService.getAllProducts(1);
        model.addAttribute("productDTOList", productDTOList);
        return "/superUser/product/editCatalog";
    }

    @PostMapping("/editSellers")
    public String showEditSellersPage(Model model) {
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/seller/editSellers";
    }
}
