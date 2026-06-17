package org.example.webApplicationShopSpringBoot.dao.userOrderProduct;


import org.example.webApplicationShopSpringBoot.dao.DAO;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;

public interface UserOrderProductDAO extends DAO<UserOrderProduct> {

    void addUserOrder(UserOrderProduct userOrderProduct, UserOrder userOrder);

    void addProduct(UserOrderProduct userOrderProduct, Product product);
}
