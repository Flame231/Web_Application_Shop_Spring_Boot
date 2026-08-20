package org.example.webApplicationShopSpringBoot.service.discount;

import org.example.webApplicationShopSpringBoot.model.Discount;
import org.example.webApplicationShopSpringBoot.model.user.User;

import java.util.List;

public interface DiscountService {

    List<Discount> getDiscountList();

    void checkUserDiscount(User user);
}
