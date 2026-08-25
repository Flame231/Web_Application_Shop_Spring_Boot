package org.example.webApplicationShopSpringBoot.controller.operatorController;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderPointDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ShowOrderDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.example.webApplicationShopSpringBoot.service.userOrderProduct.UserOrderProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("${operatorPath}")
@AllArgsConstructor
public class OperatorController {
    private UserOrderService userOrderService;
    private UserService userService;
    private UserOrderProductService userOrderProductService;
    private ArchivedUserOrderService archivedUserOrderService;

    @RequestMapping(value = "accountOperator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showOperatorPage(Model model) {
        OrderPointDTO orderPointDTO = userService.getOrderPoint();
        model.addAttribute("orderPointDTO", orderPointDTO);
        return "account/accountOperator";
    }

    @GetMapping("orders")
    public String showCreatedOrders(Model model, @AuthenticationPrincipal User user) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showCreatedUserOrders(user);
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "/order/showCreatedOrderPointOrders";
    }

    @GetMapping("arrivedOrders")
    public String showArrivedOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showReadyUserOrdersByOrderPoint();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "/order/showReadyOrderPointOrders";
    }

    @PostMapping("changeUserOrderStatus")
    public String changeUserOrderStatus(@RequestParam Long userOrderId) {
        userOrderService.readyUserOrder(userOrderId);
        return "redirect:readyOrders";
    }

    @GetMapping("readyOrders")
    public String showReadyOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showReadyUserOrdersByOrderPoint();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "/order/showReadyOrderPointOrders";
    }

    @GetMapping("showOrder")
    public String showOrder(@RequestParam Long id, Model model) {
        ShowOrderDTO showOrderDTO = userOrderService.returnOrderInfo(id);
        model.addAttribute("showOrderDTO", showOrderDTO);
        return "/order/showOrderPointOrderPage";
    }

    @PostMapping("addProductToOrder")
    public String addProductToOrder(@ModelAttribute UserOrderChangeCountDTO userOrderChangeCountDTO, @RequestParam Long id, RedirectAttributes redirectAttributes) {
        userOrderProductService.addProductToOrder(userOrderChangeCountDTO);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/operator/showOrder";
    }

    @PostMapping("deleteProductFromOrder")
    public String deleteProductFromOrder(@ModelAttribute UserOrderChangeCountDTO userOrderChangeCountDTO, @RequestParam Long id, RedirectAttributes redirectAttributes) {
        userOrderProductService.deleteProductFromOrder(userOrderChangeCountDTO);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/operator/showOrder";
    }

    @PostMapping("refuseOrder")
    public String refuseOrder(@RequestParam Long userOrderId) {
        archivedUserOrderService.refuseUserOrder(userOrderId);
        return "redirect:/operator/arrivedOrders";
    }

    @PostMapping("closeOrder")
    public String closeOrder(@RequestParam Long userOrderId) {
        archivedUserOrderService.createArchivedUserOrder(userOrderId);
        return "redirect:/operator/arrivedOrders";
    }
}
