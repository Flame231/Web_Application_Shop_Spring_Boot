package org.example.webApplicationShopSpringBoot.controller.userController;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.dto.dto.*;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.PageResponse;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;
import org.example.webApplicationShopSpringBoot.service.bag.BagService;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointService;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.BagForm;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("${clientPath}")
public class UserController {
    public static final String PAGE_SIZE_30 = "30";
    public static final List<Integer> pageSizeList = List.of(30, 50, 100);
    private final UserService userService;
    private ProductService productService;
    private BagService bagService;
    private OrderPointService orderPointService;
    private UserOrderService userOrderService;
    private ArchivedUserOrderService archivedUserOrderService;

    @RequestMapping(value = "accountClient", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage(Model model) {
        UserDiscountDTO userDiscountDTO = userService.getUserDiscount();
        model.addAttribute(userDiscountDTO);
        return "account/accountClient";
    }

    @GetMapping("catalog")
    public String showCatalog(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @AuthenticationPrincipal User user) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        PageResponse<ProductDTO> productDTOList = productService.getActiveProducts(pageable);
        List<BagDTOResponse> bagDTOResponseList = bagService.getAllBags();
        BigDecimal bagSum = bagService.showBagSum();
        model.addAttribute("page", page);
        model.addAttribute("bagSum", bagSum);
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("userId", user.getId());
        model.addAttribute("productDTOList", productDTOList);
        model.addAttribute("bagDTOResponseList", bagDTOResponseList);
        return "catalog";
    }

    @PostMapping("addProductToCatalogBag")
    public String addProductToCatalogBag(@ModelAttribute BagDTORequest bagDTORequest, @RequestParam(defaultValue = "1") int page, RedirectAttributes redirectAttributes) {
        bagService.addProductToBag(bagDTORequest);
        redirectAttributes.addAttribute("page", page);
        return "redirect:/client/catalog";
    }

    @PostMapping("deleteProductFromCatalogBag")
    public String deleteProductFromCatalogBag(@ModelAttribute BagDTORequest bagDTORequest, @RequestParam(defaultValue = "1") int page, RedirectAttributes redirectAttributes) {
        bagService.deleteProductFromBag(bagDTORequest);
        redirectAttributes.addAttribute("page", page);
        return "redirect:/client/catalog";
    }

    @GetMapping("bag")
    public String showBag(Model model) {
        List<BagDTOResponse> bagDTOResponseList = bagService.openBag();
        BigDecimal bagSum = bagService.showBagSum();
        List<OrderPointDTO> orderPointDTOList = orderPointService.getAllOrderPoints();
        model.addAttribute("bagSum", bagSum);
        model.addAttribute("bagDTOResponseList", bagDTOResponseList);
        model.addAttribute("orderPointDTOList", orderPointDTOList);
        return "/bag";
    }

    @PostMapping("addProductToBag")
    public String addProductToBag(@ModelAttribute BagDTORequest bagDTORequest) {
        bagService.addProductToBag(bagDTORequest);
        return "redirect:/client/bag";
    }

    @PostMapping("deleteProductFromBag")
    public String deleteProductFromBag(@ModelAttribute BagDTORequest bagDTORequest) {
        bagService.deleteProductFromBag(bagDTORequest);
        return "redirect:/client/bag";
    }

    @PostMapping("clearBag")
    public String clearAllBags(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "Корзина очищена!");
        bagService.clearAllBags();
        return "redirect:/client/bag";
    }

    @PostMapping("confirmOrder")
    public String confirmOrder(@ModelAttribute BagForm bagForm, RedirectAttributes redirectAttributes) {
        List<OrderDTO> list = bagForm.toNewOrderDTO();
        userOrderService.confirmOrder(list);
        redirectAttributes.addFlashAttribute("successMessage", "Заказ успешно оформлен");
        return "redirect:/client/catalog";
    }

    @GetMapping("userProfile")
    public String showUserProfile(Model model) {
        UserProfileDTO userProfileDTO = userService.getUserProfileDTO();
        model.addAttribute("userProfileDTO", userProfileDTO);
        return "userProfile";
    }

    @PostMapping("updateUser")
    public String updateUser(@ModelAttribute @Valid UserProfileDTO userProfileDTO, RedirectAttributes redirectAttributes) {
        userService.updateUser(userProfileDTO);
        redirectAttributes.addFlashAttribute("successMessage", "Данные пользователя успешно обновлены!");
        return "redirect:/client/accountClient";
    }

    @GetMapping("userOrders")
    public String showUserOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showAllUserOrders();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "order/showClientOrders";
    }

    @GetMapping("ordersHistory")
    public String showOrdersHistory(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").ascending());
        PageResponse<ArchivedUserOrderDTO> archivedUserOrderDTOList = archivedUserOrderService.showArchivedUserOrders(pageable);
        model.addAttribute("archivedUserOrderDTOList", archivedUserOrderDTOList);
        model.addAttribute("page", page);
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("pageSize", pageSize);
        return "/order/showArchivedOrders";
    }
}
