package org.example.webApplicationShopSpringBoot.controller.administratorController;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.productCategory.ProductCategoryService;
import org.example.webApplicationShopSpringBoot.service.seller.SellerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private ProductCategoryService productCategoryService;
    private SellerService sellerService;

    @GetMapping(value = "productsList")
    public String showProductsList(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, Model model) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        PageResponse<ProductDTO> productDTOList = productService.getAllProducts(pageable);
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("productDTOList", productDTOList);
        return "/superUser/product/productsList";
    }

    @GetMapping(value = "addProduct")
    public String showAddProductPage(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").ascending());
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getActiveProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getActiveSellerDTOList();
        model.addAttribute(new ProductDTO());
        model.addAttribute("productCategoryDTOList", productCategoryDTOList);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "/superUser/product/addProduct";
    }

    @RequestMapping(value = "addNewProduct", method = {RequestMethod.GET, RequestMethod.POST})
    public String addProduct(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes) {
        productService.addProduct(productDTO);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно сохранён!");
        return "redirect:/administrator/productsList";
    }

    @PostMapping("updateProduct")
    public String updateProduct(@ModelAttribute ProductDTO productDTO, RedirectAttributes redirectAttributes) {
        productService.updateProduct(productDTO);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно обновлён!");
        return "redirect:/administrator/productsList";
    }

    @PostMapping("deleteProduct/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.removeProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно удалён!");
        return "redirect:/administrator/productsList";
    }

    @PostMapping("recoverProduct/{id}")
    public String recoverProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.recoverProduct(id);
        redirectAttributes.addFlashAttribute("successMessage", "продукт успешно восстановлен!");
        return "redirect:/administrator/productsList";
    }

    @GetMapping("editProduct/{id}")
    public String showEditProductPage(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProduct(id);
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getActiveProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productCategoryDTOList", productCategoryDTOList);
        model.addAttribute("sellerDTOList", sellerDTOList);
        return "superUser/product/editProduct";
    }

    @GetMapping("productPage/{id}")
    public String showProductPage(@PathVariable Long id, Model model) {
        ProductDTO productDTO = productService.findProduct(id);
        model.addAttribute("productDTO", productDTO);
        return "/superUser/product/productPage";
    }
}
