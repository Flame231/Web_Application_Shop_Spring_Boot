package org.example.webApplicationShopSpringBoot.service.userOrder;


import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.model.OrderPoint;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;

import java.util.List;

public interface UserOrderService {

    List<UserOrderDTO> showAllUserOrders(User user);

    List<UserOrderDTO> showReadyUserOrdersByOrderPoint(User user);

    UserOrder getUserOrder(Long id);

    void readyUserOrder(Long userOrderId);

    List<UserOrderDTO> showCreatedUserOrders(User user);

    void deleteUserOrder(Long id);

    UserOrder createUserOrder(User user, OrderPoint orderPoint);
}
