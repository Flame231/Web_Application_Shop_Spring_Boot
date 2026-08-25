package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;

import java.math.BigDecimal;

public interface UserOrderProductService {

    void addProductToOrder(UserOrderChangeCountDTO userOrderChangeCountDTO);

    void deleteProductFromOrder(UserOrderChangeCountDTO userOrderChangeCountDTO);

    BigDecimal showUserOrderProductSum(Long userOrderId);

    void addBagToUserOrderProduct(Bag bag, UserOrder userOrder, User user);
}
