package org.example.webApplicationShopSpringBoot.controller.administratorController;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.NewProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.EditProductDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("${adminPath}")
public class ProductController {
    public static final String PAGE_SIZE_30 = "30";
    public static final List<Integer> pageSizeList = List.of(30, 50, 100);
    private ProductService productService;

    @GetMapping(value = "productsList")
    public String showProductsList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, Model model) {
        PageResponse<ProductDTO> productDTOList = productService.getAllProducts(page, pageSize);
        model.addAttribute("productDTOList", productDTOList);
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "/superUser/product/productsList";
    }

    @GetMapping(value = "addProduct")
    public String showAddProductPage(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, Model model) {
        EditProductDTO editProductDTO = productService.returnLists();
        model.addAttribute(new ProductDTO());
        model.addAttribute("editProductDTO", editProductDTO);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "/superUser/product/addProduct";
    }

    @RequestMapping(value = "addNewProduct", method = {RequestMethod.GET, RequestMethod.POST})
    public String addProduct(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @ModelAttribute NewProductDTO newProductDTO, RedirectAttributes redirectAttributes) {
        productService.addProduct(newProductDTO);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("pageSize", pageSize);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно сохранён!");
        return "redirect:/administrator/productsList";
    }

    @PostMapping("updateProduct")
    public String updateProduct(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @ModelAttribute NewProductDTO newProductDTO, RedirectAttributes redirectAttributes) {
        productService.updateProduct(newProductDTO);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("pageSize", pageSize);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно обновлён!");
        return "redirect:/administrator/productsList";
    }

    @PostMapping("deleteProduct/{id}")
    public String deleteProduct(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.removeProduct(id);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("pageSize", pageSize);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно удалён!");
        return "redirect:/administrator/productsList";
    }

    @PostMapping("recoverProduct/{id}")
    public String recoverProduct(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.recoverProduct(id);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("pageSize", pageSize);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно восстановлен!");
        return "redirect:/administrator/productsList";
    }

    @GetMapping("editProduct/{id}")
    public String showEditProductPage(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @PathVariable Long id, Model model) {
        EditProductDTO editProductDTO = productService.returnLists(id);
        model.addAttribute("editProductDTO", editProductDTO);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "superUser/product/editProduct";
    }

    @GetMapping("productPage/{id}")
    public String showProductPage(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProduct(id);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        return "/superUser/product/productPage";
    }
}
