package org.example.webApplicationShopSpringBoot.dao.userOrderProduct;


import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderProductRepository extends JpaRepository<UserOrderProduct, PrimaryKeyUserOrderProduct> {
}
