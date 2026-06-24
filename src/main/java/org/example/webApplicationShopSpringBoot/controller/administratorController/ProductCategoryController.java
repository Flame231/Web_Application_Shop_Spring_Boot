package org.example.webApplicationShopSpringBoot.controller.administratorController;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("${admin.path}")
@AllArgsConstructor
public class ProductCategoryController {
    private ProductCategoryService productCategoryService;

    @GetMapping("editProductCategories")
    public String showEditProductCategoriesPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").ascending());
        Page<ProductCategoryDTO> productCategoriesList = productCategoryService.getProductCategoryDTOList(pageable);
        model.addAttribute("productCategoriesList", productCategoriesList);
        return "superUser/productCategory/editProductCategories";
    }
    @GetMapping("addProductCategory")
    public String showAddProductCategoryPage() {

        return "/superUser/productCategory/addProductCategory";
    }

    @PostMapping("addNewProductCategory")
    public String addNewProductCategory(@ModelAttribute ProductCategoryDTO productCategoryDTO) {
        productCategoryService.addProductCategory(productCategoryDTO);
        return "redirect:editProductCategories";
    }

    @PostMapping("deleteProductCategory/{id}")
    public String deleteProductCategory(@PathVariable Long id) {
        productCategoryService.deleteProductCategory(id);
        return "redirect:/editProductCategories";
    }
    @GetMapping("editProductCategory/{id}")
    public String showEditProductCategoryPage(@PathVariable Long id, Model model) {
        ProductCategoryDTO productCategoryDTO = productCategoryService.findProductCategory(id);
        model.addAttribute("productCategoryDTO", productCategoryDTO);
        return "/superUser/productCategory/editProductCategory";
    }

    @PostMapping("updateProductCategory")
    public String updateProductCategory(@ModelAttribute ProductCategoryDTO productCategoryDTO) {
        productCategoryService.updateProductCategory(productCategoryDTO);
        return "redirect:/editProductCategories";
    }
}
