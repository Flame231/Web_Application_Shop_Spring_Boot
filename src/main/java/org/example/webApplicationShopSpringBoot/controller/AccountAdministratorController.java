package org.example.webApplicationShopSpringBoot.controller;


import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AccountAdministratorController {

    private ProductCategoryService productCategoryService;
    private ProductService productService;
    private SellerService sellerService;

    public AccountAdministratorController(ProductCategoryService productCategoryService, SellerService sellerService, ProductService productService) {
        this.productCategoryService = productCategoryService;
        this.productService = productService;
        this.sellerService = sellerService;
        this.productService = productService;
    }

    @GetMapping("/editProductCategories")
    public String showEditProductCategoriesPage(Model model) {
        List<ProductCategoryDTO> productCategoriesList = productCategoryService.getProductCategoryDTOList();
        model.addAttribute("productCategoriesList", productCategoriesList);
        return "superUser/productCategory/editProductCategories";
    }

    @RequestMapping(value = "/accountAdministrator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage(Model model) {
        return "account/accountAdministrator";
    }

    @GetMapping(value = "/editCatalog")
    public String showEditCatalogPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").ascending());
        Page<ProductDTO> productDTOList = productService.getAllProducts(pageable);
        model.addAttribute("productDTOList", productDTOList);
        return "/superUser/product/editCatalog";
    }

    @GetMapping("/editSellers")
    public String showEditSellersPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").ascending());
        Page<SellerDTO> sellerDTOList = sellerService.getSellerDTOList(pageable);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/seller/editSellers";
    }

    @RequestMapping(value = "/addProduct", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAddProductPage(Model model) {
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("productCategoryDTOList", productCategoryDTOList);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/product/addProduct";
    }

    @RequestMapping(value = "/addNewProduct", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNewProduct(@ModelAttribute ProductDTO productDTO) {
        productService.addProduct(productDTO);
        return "redirect:/editCatalog";
    }

    @GetMapping("/editProduct/{id}")
    public String showEditProductPage(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProduct(id);
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productCategoryDTOList", productCategoryDTOList);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "superUser/product/editProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return "redirect:editCatalog";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return "redirect:/editCatalog";
    }

    @GetMapping("/productPage/{id}")
    public String showProductPage(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProduct(id);
        model.addAttribute("productDTO", productDTO);
        return "/superUser/product/productPage";
    }

    @PostMapping("/deleteProductCategory/{id}")
    public String deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return "redirect:/editProductCategories";
    }

    @PostMapping("/deleteSeller/{id}")
    public String deleteSeller(@PathVariable Long id) {
        sellerService.removeSeller(id);
        return "redirect:/editSellers";
    }

}
