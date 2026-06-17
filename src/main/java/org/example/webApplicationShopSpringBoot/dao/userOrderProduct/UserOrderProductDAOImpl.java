package org.example.webApplicationShopSpringBoot.dao.userOrderProduct;


import org.example.webApplicationShopSpringBoot.dao.DAOImpl;
import org.example.webApplicationShopSpringBoot.model.Product;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("userOrderProductDAO")
public class UserOrderProductDAOImpl extends DAOImpl<UserOrderProduct> implements UserOrderProductDAO {
    public UserOrderProductDAOImpl() {
        super(UserOrderProduct.class);
    }

    @Override
    public void addUserOrder(UserOrderProduct userOrderProduct, UserOrder userOrder) {
        userOrderProduct.setUserOrder(userOrder);
        userOrder.getUserOrderProduct().add(userOrderProduct);
    }

    @Override
    public void addProduct(UserOrderProduct userOrderProduct, Product product) {
        userOrderProduct.setProduct(product);
        product.getUserOrderProducts().add(userOrderProduct);
    }

}
