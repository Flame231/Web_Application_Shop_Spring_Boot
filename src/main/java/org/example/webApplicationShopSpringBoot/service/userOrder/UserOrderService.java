package org.example.webApplicationShopSpringBoot.service.userOrder;


import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;

import java.util.List;

public interface UserOrderService {

    void confirmOrder(List<OrderDTO> list);

    List<UserOrderDTO> showAllUserOrders();

    List<UserOrderDTO> showUserOrdersByOrderPoint();

    List<UserOrderDTO> showReadyUserOrdersByOrderPoint();

    UserOrderDTO getUserOrderDTO(Long  id);

    void readyUserOrder(Long userOrderId);

    List<UserOrderDTO> showCreatedUserOrders();
}
