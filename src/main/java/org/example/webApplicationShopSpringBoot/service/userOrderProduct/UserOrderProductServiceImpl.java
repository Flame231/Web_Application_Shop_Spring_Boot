package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.repository.user.UserRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.service.Calculate;
import org.example.webApplicationShopSpringBoot.service.PrincipalProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class UserOrderProductServiceImpl implements UserOrderProductService {
    private UserOrderProductRepository userOrderProductRepository;
    private UserOrderRepository userOrderRepository;
    private UserRepository userRepository;

    @Override
    public void addProductToOrder(UserOrderChangeCountDTO userOrderChangeCountDTO) {
        Long userOrderId = userOrderChangeCountDTO.getUserOrderId();
        Long productId = userOrderChangeCountDTO.getProductId();
        Long count = userOrderChangeCountDTO.getCount();
        PrimaryKeyUserOrderProduct primaryKeyUserOrderProduct =
                PrimaryKeyUtil.getPrimaryKeyUserOrderProduct(userOrderId, productId);
        UserOrderProduct userOrderProduct = userOrderProductRepository.findById(primaryKeyUserOrderProduct).get();
        if (userOrderProduct.getActualProductCount() + count <= userOrderProduct.getProductCount()) {
            Long newCount = userOrderProduct.getActualProductCount() + count;
            userOrderProduct.setActualProductCount(newCount);
            userOrderProductRepository.flush();
        }
        log.info("Значение продукта с id {} успешно увеличено на {} в заказе с id {}!", productId, count, userOrderId);
    }

    @Override
    public void deleteProductFromOrder(UserOrderChangeCountDTO userOrderChangeCountDTO) {
        Long userOrderId = userOrderChangeCountDTO.getUserOrderId();
        Long productId = userOrderChangeCountDTO.getProductId();
        Long count = userOrderChangeCountDTO.getCount();
        PrimaryKeyUserOrderProduct primaryKeyUserOrderProduct =
                PrimaryKeyUtil.getPrimaryKeyUserOrderProduct(userOrderId, productId);
        UserOrderProduct userOrderProduct = userOrderProductRepository.findById(primaryKeyUserOrderProduct).get();
        if (userOrderProduct.getActualProductCount() - count >= 0) {
            Long newCount = userOrderProduct.getActualProductCount() - count;
            userOrderProduct.setActualProductCount(newCount);
            userOrderProductRepository.flush();
        }
        log.info("Значение продукта с id {} успешно уменьшено на {} в заказе с id {}!", productId, count, userOrderId);
    }

    @Override
    public BigDecimal showUserOrderProductSum(Long userOrderId) {
        UserOrder userOrder = userOrderRepository.findById(userOrderId).get();
        User user = userRepository.findById(userOrder.getUser().getId()).get();
        List<UserOrderProduct> userOrderProductList = userOrderProductRepository.findByUserOrderId(userOrderId);
        Stream<UserOrderProduct> userOrderProductStream = userOrderProductList.stream();
        return Calculate.calculateSum(userOrderProductStream, user.getDiscount().getDiscount());
    }
}
