package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import org.example.webApplicationShopSpringBoot.dao.userOrderProduct.UserOrderProductDAO;
import org.example.webApplicationShopSpringBoot.dao.userOrderProduct.UserOrderProductDAOImpl;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;

public class UserOrderProductServiceImpl implements UserOrderProductService {
    private UserOrderProductDAO userOrderProductDAO = new UserOrderProductDAOImpl();

    @Override
    public void changeProductCount(Integer userOrderId, Integer productId,
                                   Integer count) {
        PrimaryKeyUserOrderProduct primaryKeyUserOrderProduct =
                PrimaryKeyUtil.getPrimaryKeyUserOrderProduct(userOrderId, productId);
        UserOrderProduct userOrderProduct = userOrderProductDAO.get(primaryKeyUserOrderProduct);
        userOrderProductDAO.refresh(userOrderProduct);
        if (userOrderProduct.getActualProductCount() + count >= 0) {
            if (userOrderProduct.getActualProductCount() + count <= userOrderProduct.getProductCount()) {
                Integer newCount = userOrderProduct.getActualProductCount() + count;

                userOrderProduct.setActualProductCount(newCount);
                userOrderProductDAO.flush();
            }
        }
    }
}
