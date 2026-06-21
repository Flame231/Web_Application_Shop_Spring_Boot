package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import org.example.webApplicationShopSpringBoot.dao.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.springframework.stereotype.Service;

@Service
public class UserOrderProductServiceImpl implements UserOrderProductService {
    private UserOrderProductRepository userOrderProductRepository;


    public UserOrderProductServiceImpl(UserOrderProductRepository userOrderProductRepository) {
        this.userOrderProductRepository = userOrderProductRepository;
    }

    @Override
    public void changeProductCount(Long userOrderId, Long productId,
                                   Long count) {
        PrimaryKeyUserOrderProduct primaryKeyUserOrderProduct =
                PrimaryKeyUtil.getPrimaryKeyUserOrderProduct(userOrderId, productId);
        UserOrderProduct userOrderProduct = userOrderProductRepository.findById(primaryKeyUserOrderProduct).get();

        if (userOrderProduct.getActualProductCount() + count >= 0) {
            if (userOrderProduct.getActualProductCount() + count <= userOrderProduct.getProductCount()) {
                Long newCount = userOrderProduct.getActualProductCount() + count;

                userOrderProduct.setActualProductCount(newCount);
                userOrderProductRepository.flush();
            }
        }
    }
}
