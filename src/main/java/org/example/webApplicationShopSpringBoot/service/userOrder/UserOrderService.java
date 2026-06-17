package org.example.webApplicationShopSpringBoot.service.userOrder;


import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;

import java.io.Serializable;
import java.util.List;

public interface UserOrderService {

    void confirmOrder(List<OrderDTO> list);

    List<UserOrderDTO> showAllUserOrders();

    List<UserOrderDTO> showUserOrdersByOrderPoint(Long userId);

    List<UserOrderDTO> showArrivedUserOrdersByOrderPoint(Long userId);

    UserOrderDTO getUserOrderDTO(Long  id);

}
