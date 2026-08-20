package org.example.webApplicationShopSpringBoot.repository.userOrderProduct;


import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderProductRepository extends JpaRepository<UserOrderProduct, PrimaryKeyUserOrderProduct> {

    List<UserOrderProduct> findByUserOrderId(Long userOrderId);
}
