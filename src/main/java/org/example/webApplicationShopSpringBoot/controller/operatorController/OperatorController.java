package org.example.webApplicationShopSpringBoot.controller.operatorController;


import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class OperatorController {

    private UserOrderService userOrderService;

    public OperatorController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @RequestMapping(value = "/operator/accountOperator", method = {RequestMethod.GET, RequestMethod.POST})
    public String showAdministratorPage() {
        return "account/accountOperator";
    }

    @GetMapping("/operator/orders")
    public String showOrders(Model model) {
        List<UserOrderDTO> userOrderDTOList = userOrderService.showUserOrdersByOrderPoint();
        model.addAttribute("userOrderDTOList", userOrderDTOList);
        return "order/showOrderPointOrders";
    }
}
