package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

public interface UserOrderProductService {

    void changeProductCount(Integer UserOrderId, Integer productId, Integer count);
}
