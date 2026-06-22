package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;

public interface UserOrderProductService {

    void changeProductCount(UserOrderChangeCountDTO userOrderChangeCountDTO);
}
