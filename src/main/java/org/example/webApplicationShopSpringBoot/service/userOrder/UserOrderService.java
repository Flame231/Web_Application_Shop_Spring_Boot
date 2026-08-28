package org.example.webApplicationShopSpringBoot.service.userOrder;


import org.example.webApplicationShopSpringBoot.dto.dto.BagFormDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ShowOrderDTO;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.util.List;

public interface UserOrderService {

    void confirmOrder(BagFormDTO bagFormDTO, User user);

    List<UserOrderDTO> showAllUserOrders(User user);

    List<UserOrderDTO> showReadyUserOrdersByOrderPoint(User user);

    UserOrderDTO getUserOrderDTO(Long id);

    void readyUserOrder(Long userOrderId);

    List<UserOrderDTO> showCreatedUserOrders(User user);

    ShowOrderDTO returnOrderInfo(Long id);

    List<OrderDTO> toNewOrderDTO(BagFormDTO bagFormDTO, User user);
}
