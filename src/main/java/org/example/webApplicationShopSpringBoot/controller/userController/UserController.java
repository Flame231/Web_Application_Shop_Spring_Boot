package org.example.webApplicationShopSpringBoot.controller.userController;


import org.example.webApplicationShopSpringBoot.dto.dto.*;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTORequest;
import org.example.webApplicationShopSpringBoot.dto.dto.BagDTO.BagDTOResponse;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.bag.BagService;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointService;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.BagForm;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    public static final String PAGE_SIZE_30 = "30";
    public static final List<Integer> pageSizeList = List.of(30, 50, 100);
    private final UserService userService;
    private ProductService productService;
    private BagService bagService;
    private OrderPointService orderPointService;
    private UserOrderService userOrderService;

    public UserController(ProductService productService, BagService bagService, OrderPointService orderPointService, UserOrderService userOrderService, UserService userService) {
        this.productService = productService;
        this.bagService = bagService;
        this.orderPointService = orderPointService;
        this.userOrderService = userOrderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/client/accountClient", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountClient";
    }

    @GetMapping("/client/catalog")
    public String showCatalog(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = PAGE_SIZE_30) int pageSize, @AuthenticationPrincipal User user) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").ascending());
        Page<ProductDTO> productDTOList = productService.getAllProducts(pageable);
        List<BagDTOResponse> bagDTOResponseList = bagService.showAllBags();
        model.addAttribute("pageSizeList", pageSizeList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("userId", user.getId());
        model.addAttribute("productDTOList", productDTOList);
        model.addAttribute("bagDTOResponseList", bagDTOResponseList);
        return "catalog";
    }

    @PostMapping("/addProductToBag")
    public String addProductToBag(@ModelAttribute BagDTORequest bagDTORequest, int page, RedirectAttributes redirectAttributes) {
        bagService.addProductToBag(bagDTORequest);
        redirectAttributes.addAttribute("page", page);
        return "redirect:/client/catalog";
    }

    @PostMapping("/deleteProductFromBag")
    public String deleteProductFromBag(@ModelAttribute BagDTORequest bagDTORequest, int page, RedirectAttributes redirectAttributes) {
        bagService.deleteProductFromBag(bagDTORequest);
        redirectAttributes.addAttribute("page", page);
        return "redirect:/client/catalog";
    }

    @GetMapping("/client/bag")
    public String showBag(Model model) {
        List<BagDTOResponse> bagDTOResponseList = bagService.showAllBags();
        BigDecimal bagSum = bagService.showBagSum();
        List<OrderPointDTO> orderPointDTOList = orderPointService.getAllOrderPoints();
        model.addAttribute("bagSum", bagSum);
        model.addAttribute("bagDTOResponseList", bagDTOResponseList);
        model.addAttribute("orderPointDTOList", orderPointDTOList);
        return "/bag";
    }

    @PostMapping("/addProductToBag1")
    public String addProductToBag1(@ModelAttribute BagDTORequest bagDTORequest) {
        bagService.addProductToBag(bagDTORequest);
        return "redirect:/client/bag";
    }

    @PostMapping("/deleteProductFromBag1")
    public String deleteProductFromBag1(@ModelAttribute BagDTORequest bagDTORequest) {
        bagService.deleteProductFromBag(bagDTORequest);
        return "redirect:/client/bag";
    }

    @PostMapping("/client/clearBag")
    public String clearAllBags() {
        bagService.clearAllBags();
        return "redirect:/client/bag";
    }

    @PostMapping("/client/confirmOrder")
    public String confirmOrder(@ModelAttribute BagForm bagForm) {
        List<OrderDTO> list = bagForm.toNewOrderDTO();
        userOrderService.confirmOrder(list);
        return "redirect:/client/catalog";
    }

    @GetMapping("/client/userProfile")
    public String showUserProfile(Model model) {
        UserDTO userDTO = userService.getUser();
        model.addAttribute("userDTO", userDTO);
        return "userProfile";
    }

    @PostMapping("/client/updateUser")
    public String updateUser(@ModelAttribute UserDTO userDTO) {
        userService.updateUser(userDTO);
        return "/account/accountClient";
    }

    @GetMapping("/client/userOrders")
    public String showUserOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showAllUserOrders();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "/order/orders";
    }
}
