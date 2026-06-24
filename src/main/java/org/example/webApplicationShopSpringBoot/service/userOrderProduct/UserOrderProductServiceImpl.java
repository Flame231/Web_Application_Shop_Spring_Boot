package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import lombok.AllArgsConstructor;
import org.example.webApplicationShopSpringBoot.dao.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.model.UserOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserOrderProductServiceImpl implements UserOrderProductService {
    private UserOrderProductRepository userOrderProductRepository;

    @Override
    public void changeProductCount(UserOrderChangeCountDTO userOrderChangeCountDTO) {
        Long userOrderId = userOrderChangeCountDTO.getUserOrderId();
        Long productId = userOrderChangeCountDTO.getProductId();
        Long count = userOrderChangeCountDTO.getCount();
        System.out.println(userOrderId + " " + productId + " " + count);
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
