package org.example.webApplicationShopSpringBoot.controller.administratorController;

import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.example.webApplicationShopSpringBoot.controller.ControllerUtil.ADMINISTRATOR_PREFIX;

@Controller
public class ProductCategoryController {

    private ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }


    @GetMapping(ADMINISTRATOR_PREFIX + "editProductCategories")
    public String showEditProductCategoriesPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").ascending());
        Page<ProductCategoryDTO> productCategoriesList = productCategoryService.getProductCategoryDTOList(pageable);
        model.addAttribute("productCategoriesList", productCategoriesList);
        return "superUser/productCategory/editProductCategories";
    }
    @GetMapping(ADMINISTRATOR_PREFIX + "addProductCategory")
    public String showAddProductCategoryPage() {
        return "/superUser/productCategory/addProductCategory";
    }

    @PostMapping(ADMINISTRATOR_PREFIX + "addNewProductCategory")
    public String addNewProductCategory(@ModelAttribute ProductCategoryDTO productCategoryDTO) {
        productCategoryService.addProductCategory(productCategoryDTO);
        return "redirect:editProductCategories";
    }

    @PostMapping(ADMINISTRATOR_PREFIX + "deleteProductCategory/{id}")
    public String deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return "redirect:/editProductCategories";
    }
    @GetMapping(ADMINISTRATOR_PREFIX + "editProductCategory/{id}")
    public String showEditProductCategoryPage(@PathVariable Long id, Model model) {
        ProductCategoryDTO productCategoryDTO = productCategoryService.findProductCategory(id);
        model.addAttribute("productCategoryDTO", productCategoryDTO);
        return "/superUser/productCategory/editProductCategory";
    }

    @PostMapping(ADMINISTRATOR_PREFIX + "updateProductCategory")
    public String updateProductCategory(@ModelAttribute ProductCategoryDTO productCategoryDTO) {
        productCategoryService.updateProductCategory(productCategoryDTO);
        return "redirect:/editProductCategories";
    }
}
