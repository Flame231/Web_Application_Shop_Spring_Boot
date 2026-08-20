package org.example.webApplicationShopSpringBoot.model.additional.primaryKeys;


import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.user.User;

public class PrimaryKeyUtil {
    public static PrimaryKeyBag getPrimaryKeyBag(User user, Product product) {
        return PrimaryKeyBag.builder().user(user.getId()).product(product.getId()).build();
    }

    public static PrimaryKeyUserOrderProduct getPrimaryKeyUserOrderProduct(Long userOrderId, Long productId) {
        return PrimaryKeyUserOrderProduct.builder().userOrder(userOrderId).product(productId).build();
    }
}
