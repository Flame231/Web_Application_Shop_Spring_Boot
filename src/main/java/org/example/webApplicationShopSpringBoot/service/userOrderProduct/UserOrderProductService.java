package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;

public interface UserOrderProductService {

    void addProductToOrder(UserOrderChangeCountDTO userOrderChangeCountDTO);

    void deleteProductFromOrder(UserOrderChangeCountDTO userOrderChangeCountDTO);
}
