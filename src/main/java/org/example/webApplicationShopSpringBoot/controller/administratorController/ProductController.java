package org.example.webApplicationShopSpringBoot.controller.administratorController;

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
public class ProductController {
    private ProductService productService;
    private ProductCategoryService productCategoryService;
    private SellerService sellerService;

    public ProductController(ProductService productService, ProductCategoryService productCategoryService, SellerService sellerService) {
        this.productService = productService;
        this.productCategoryService = productCategoryService;
        this.sellerService = sellerService;
    }

    @GetMapping(value = "/administrator/editCatalog")
    public String showEditCatalogPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").ascending());
        Page<ProductDTO> productDTOList = productService.getAllProducts(pageable);
        model.addAttribute("productDTOList", productDTOList);
        return "/superUser/product/editCatalog";
    }

    @GetMapping(value = "/administrator/addProduct")
    public String showAddProductPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").ascending());
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("productCategoryDTOList", productCategoryDTOList);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/product/addProduct";
    }

    @RequestMapping(value = "/administrator/addNewProduct", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNewProduct(@ModelAttribute ProductDTO productDTO) {
        productService.addProduct(productDTO);
        return "redirect:/editCatalog";
    }

    @PostMapping("/administrator/updateProduct")
    public String updateProduct(@ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return "redirect:editCatalog";
    }

    @PostMapping("/administrator/deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return "redirect:/editCatalog";
    }

    @GetMapping("/administrator/editProduct/{id}")
    public String showEditProductPage(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProduct(id);
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productCategoryDTOList", productCategoryDTOList);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "superUser/product/editProduct";
    }

    @GetMapping("/administrator/productPage/{id}")
    public String showProductPage(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProduct(id);
        model.addAttribute("productDTO", productDTO);
        return "/superUser/product/productPage";
    }
}
