package org.example.webApplicationShopSpringBoot.controller.userController;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.*;
import org.example.webApplicationShopSpringBoot.dto.dto.bagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.BagInfoDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ProductsAndBagsDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;
import org.example.webApplicationShopSpringBoot.service.bag.BagService;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("${clientPath}")
public class UserController {
    public static final String PAGE_SIZE_30 = "30";
    public static final List<Integer> pageSizeList = List.of(30, 50, 100);
    private final UserService userService;
    private final ProductService productService;
    private final BagService bagService;
    private final UserOrderService userOrderService;
    private final ArchivedUserOrderService archivedUserOrderService;

    @RequestMapping(value = "accountClient", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage(Model model, @AuthenticationPrincipal User user) {
        UserDiscountDTO userDiscountDTO = userService.getUserDiscount(user);
        model.addAttribute("userDiscountDTO", userDiscountDTO);
        return "account/accountClient";
    }

    @GetMapping("catalog")
    public String showCatalog(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @AuthenticationPrincipal User user) {
        ProductsAndBagsDTO productsAndBagsDTO = productService.getProductsAndBags(page, pageSize, user);
        model.addAttribute("productsAndBagsDTO", productsAndBagsDTO);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSizeList", pageSizeList);
        return "catalog";
    }

    @PostMapping("addProductToCatalogBag")
    public String addProductToCatalogBag(@ModelAttribute BagDTORequest bagDTORequest, @RequestParam(defaultValue = "1") int page, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        bagService.addProductToBag(bagDTORequest, user);
        redirectAttributes.addAttribute(page);
        return "redirect:/client/catalog";
    }

    @PostMapping("deleteProductFromCatalogBag")
    public String deleteProductFromCatalogBag(@ModelAttribute BagDTORequest bagDTORequest, @RequestParam(defaultValue = "1") int page, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        bagService.deleteProductFromBag(bagDTORequest, user);
        redirectAttributes.addAttribute(page);
        return "redirect:/client/catalog";
    }

    @GetMapping("bag")
    public String showBag(Model model, @AuthenticationPrincipal User user) {
        BagInfoDTO bagInfoDTO = bagService.returnBagInfo(user);
        model.addAttribute("bagInfoDTO", bagInfoDTO);
        return "/bag";
    }

    @PostMapping("addProductToBag")
    public String addProductToBag(@ModelAttribute BagDTORequest bagDTORequest, @AuthenticationPrincipal User user) {
        bagService.addProductToBag(bagDTORequest, user);
        return "redirect:/client/bag";
    }

    @PostMapping("deleteProductFromBag")
    public String deleteProductFromBag(@ModelAttribute BagDTORequest bagDTORequest, @AuthenticationPrincipal User user) {
        bagService.deleteProductFromBag(bagDTORequest, user);
        return "redirect:/client/bag";
    }

    @PostMapping("clearBag")
    public String clearAllBags(RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        redirectAttributes.addFlashAttribute("successMessage", "Корзина очищена!");
        bagService.clearAllBags(user);
        return "redirect:/client/bag";
    }

    @PostMapping("confirmOrder")
    public String confirmOrder(@ModelAttribute BagFormDTO bagFormDTO, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        userOrderService.confirmOrder(bagFormDTO, user);
        redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно оформлен");
        return "redirect:/client/catalog";
    }

    @GetMapping("userProfile")
    public String showUserProfile(Model model, @AuthenticationPrincipal User user) {
        UserProfileDTO userProfileDTO = userService.getUserProfileDTO(user);
        model.addAttribute("userProfileDTO", userProfileDTO);
        return "userProfile";
    }

    @PostMapping("updateUser")
    public String updateUser(@ModelAttribute @Valid UserProfileDTO userProfileDTO, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {
        userService.updateUser(userProfileDTO, user);
        redirectAttributes.addFlashAttribute("successMessage", "Данные пользователя успешно обновлены!");
        return "redirect:/client/accountClient";
    }

    @GetMapping("userOrders")
    public String showUserOrders(Model model, @AuthenticationPrincipal User user) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showAllUserOrders(user);
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "order/showClientOrders";
    }

    @GetMapping("ordersHistory")
    public String showOrdersHistory(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @AuthenticationPrincipal User user) {
        PageResponse<ArchivedUserOrderDTO> archivedUserOrderDTOList = archivedUserOrderService.showArchivedUserOrders(page, pageSize, user);
        model.addAttribute("archivedUserOrderDTOList", archivedUserOrderDTOList);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageSizeList", pageSizeList);
        return "/order/showArchivedOrders";
    }
}
