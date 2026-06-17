package org.example.webApplicationShopSpringBoot.controller;


import jakarta.transaction.Transactional;
import org.example.webApplicationShopSpringBoot.dao.productCategory.ProductCategoryRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/accountAdministrator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage(Model model) {
        return "account/accountAdministrator";
    }

    @RequestMapping(value = "/editCatalog", method = {RequestMethod.GET, RequestMethod.POST})
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

    @RequestMapping(value = "/addProduct", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAddProductPage(Model model) {
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("productCategoryDTOList",productCategoryDTOList);
        model.addAttribute("sellerDTOList",sellerDTOList);
        return "/superUser/product/addProduct";
    }

    @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute ProductDTO productDTO){
        productService.addProduct(productDTO);
        return "redirect:/editCatalog";
    }
}
