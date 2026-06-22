package org.example.webApplicationShopSpringBoot.controller.operatorController;


import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.example.webApplicationShopSpringBoot.service.userOrderProduct.UserOrderProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OperatorController {

    private UserOrderService userOrderService;
    private UserOrderProductService userOrderProductService;
    private ArchivedUserOrderService archivedUserOrderService;

    public OperatorController(UserOrderService userOrderService, UserOrderProductService userOrderProductService, ArchivedUserOrderService archivedUserOrderService) {
        this.userOrderService = userOrderService;
        this.userOrderProductService = userOrderProductService;
        this.archivedUserOrderService = archivedUserOrderService;
    }

    @RequestMapping(value = "/operator/accountOperator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountOperator";
    }

    @GetMapping("/operator/orders")
    public String showCreatedOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showCreatedUserOrders();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "/order/showCreatedOrderPointOrders";
    }

    @GetMapping("/operator/arrivedOrders")
    public String showArrivedOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showReadyUserOrdersByOrderPoint();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "/order/showReadyOrderPointOrders";
    }

    @PostMapping("operator/changeUserOrderStatus")
    public String changeUserOrderStatus(@RequestParam Long userOrderId) {
        userOrderService.readyUserOrder(userOrderId);
        return "redirect:/operator/orders";
    }

    @GetMapping("/operator/readyOrders")
    public String showReadyOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showReadyUserOrdersByOrderPoint();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "/order/showReadyOrderPointOrders";
    }

    @GetMapping("/operator/showOrder")
    public String showOrder(@RequestParam Long id, Model model) {
        UserOrderDTO userOrderDTO = userOrderService.getUserOrderDTO(id);
        model.addAttribute("userOrderDTO", userOrderDTO);
        return "/order/showOrderPointOrderPage";
    }

    @PostMapping("/operator/addProductToOrder")
    public String addProductToOrder(@ModelAttribute UserOrderChangeCountDTO userOrderChangeCountDTO, @RequestParam Long id, RedirectAttributes redirectAttributes) {
        userOrderProductService.changeProductCount(userOrderChangeCountDTO);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/operator/showOrder";
    }

    @PostMapping("/operator/refuseOrder")
    public String refuseOrder(@RequestParam Long userOrderId) {
        archivedUserOrderService.refuseUserOrder(userOrderId);
        return "redirect:/operator/arrivedOrders";
    }

    @PostMapping("/operator/closeOrder")
    public String closeOrder(@RequestParam Long userOrderId) {
        archivedUserOrderService.createArchivedUserOrder(userOrderId);
        return "redirect:/operator/arrivedOrders";
    }
}
