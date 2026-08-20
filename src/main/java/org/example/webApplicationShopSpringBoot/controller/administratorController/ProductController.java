package org.example.webApplicationShopSpringBoot.controller.administratorController;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductCategoryDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.ProductDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.SellerDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.NewProductDTO;
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
    public String showProductsList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, Model model) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        PageResponse<ProductDTO> productDTOList = productService.getAllProducts(pageable);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("productDTOList", productDTOList);
        return "/superUser/product/productsList";
    }

    @GetMapping(value = "addProduct")
    public String showAddProductPage(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").ascending());
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getActiveProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getActiveSellerDTOList();
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute(new ProductDTO());
        model.addAttribute(productCategoryDTOList);
        model.addAttribute(sellerDTOList);
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
        System.out.println(newProductDTO);
        productService.updateProduct(newProductDTO);
        System.out.println(page + " " + pageSize);
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
        ProductDTO productDTO = productService.findProduct(id);
        List<ProductCategoryDTO> productCategoryDTOList = productCategoryService.getActiveProductCategoryDTOList();
        List<SellerDTO> sellerDTOList = sellerService.getSellerDTOList();
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productCategoryDTOList", productCategoryDTOList);
        model.addAttribute("sellerDTOList", sellerDTOList);
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
