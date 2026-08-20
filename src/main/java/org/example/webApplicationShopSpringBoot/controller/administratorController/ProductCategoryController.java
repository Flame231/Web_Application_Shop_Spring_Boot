package org.example.webApplicationShopSpringBoot.controller.administratorController;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("${adminPath}")
@AllArgsConstructor
public class ProductCategoryController {
    public static final String PAGE_SIZE_30 = "30";
    public static final List<Integer> pageSizeList = List.of(30, 50, 100);
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "productCategoriesList", method = {RequestMethod.GET, RequestMethod.POST})
    public String showProductCategoriesList(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, Model model) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        PageResponse<ProductCategoryDTO> productCategoriesList = productCategoryService.getProductCategoryDTOList(pageable);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("productCategoriesList", productCategoriesList);
        return "/superUser/productCategory/productCategoriesList";
    }

    @GetMapping("addProductCategory")
    public String showAddProductCategoryPage(Model model) {
        model.addAttribute(new ProductCategoryDTO());
        return "/superUser/productCategory/addProductCategory";
    }

    @PostMapping("addNewProductCategory")
    public String addNewProductCategory(@ModelAttribute ProductCategoryDTO productCategoryDTO, RedirectAttributes redirectAttributes) {
        productCategoryService.addProductCategory(productCategoryDTO);
        redirectAttributes.addFlashAttribute("successMessage", "категория успешно добавлена!");
        return "redirect:editProductCategories";
    }

    @PostMapping("deleteProductCategory/{id}")
    public String deleteProductCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productCategoryService.deleteProductCategory(id);
        redirectAttributes.addFlashAttribute("successMessage", "категория успешно удалена!");
        return "redirect:/administrator/editProductCategories";
    }

    @PostMapping("recoverProductCategory/{id}")
    public String recoverProductCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productCategoryService.recoverProductCategory(id);
        redirectAttributes.addFlashAttribute("successMessage", "категория успешно удалена!");
        return "redirect:/administrator/editProductCategories";
    }

    @GetMapping("editProductCategory/{id}")
    public String showEditProductCategoryPage(@PathVariable Long id, Model model) {
        ProductCategoryDTO productCategoryDTO = productCategoryService.findProductCategory(id);
        model.addAttribute("productCategoryDTO", productCategoryDTO);
        return "/superUser/productCategory/editProductCategory";
    }

    @PostMapping("updateProductCategory")
    public String updateProductCategory(@ModelAttribute ProductCategoryDTO productCategoryDTO, RedirectAttributes redirectAttributes) {
        productCategoryService.updateProductCategory(productCategoryDTO);
        redirectAttributes.addFlashAttribute("successMessage", "категория успешно обновлена!");
        return "redirect:editProductCategories";
    }
}
