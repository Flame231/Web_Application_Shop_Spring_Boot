package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;

import java.math.BigDecimal;

public interface UserOrderProductService {

    void addProductToOrder(UserOrderChangeCountDTO userOrderChangeCountDTO);

    void deleteProductFromOrder(UserOrderChangeCountDTO userOrderChangeCountDTO);

    BigDecimal showUserOrderProductSum(Long userOrderId);
}
