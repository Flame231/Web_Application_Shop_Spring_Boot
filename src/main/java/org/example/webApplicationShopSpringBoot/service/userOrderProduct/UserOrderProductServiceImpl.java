package org.example.webApplicationShopSpringBoot.service.userOrderProduct;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderChangeCountDTO;
import org.example.webApplicationShopSpringBoot.model.Bag;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyUtil;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.repository.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.service.Calculate;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.ResourceNotFound;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserOrderProductServiceImpl implements UserOrderProductService {

    private final UserOrderService userOrderService;
    private final UserOrderProductRepository userOrderProductRepository;

    @Override
    public void addProductToOrder(UserOrderChangeCountDTO userOrderChangeCountDTO) {
        Long userOrderId = userOrderChangeCountDTO.getUserOrderId();
        Long productId = userOrderChangeCountDTO.getProductId();
        Long count = userOrderChangeCountDTO.getCount();
        PrimaryKeyUserOrderProduct primaryKeyUserOrderProduct =
                PrimaryKeyUtil.getPrimaryKeyUserOrderProduct(userOrderId, productId);
        UserOrderProduct userOrderProduct = getUserOrderProduct(primaryKeyUserOrderProduct);
        if (userOrderProduct.getActualProductCount() + count <= userOrderProduct.getProductCount()) {
            Long newCount = userOrderProduct.getActualProductCount() + count;
            userOrderProduct.setActualProductCount(newCount);
            userOrderProductRepository.flush();
            log.info("Значение продукта с id {} успешно увеличено до {} в заказе с id {}!", productId, newCount, userOrderId);
        }
    }

    @Override
    public void deleteProductFromOrder(UserOrderChangeCountDTO userOrderChangeCountDTO) {
        Long userOrderId = userOrderChangeCountDTO.getUserOrderId();
        Long productId = userOrderChangeCountDTO.getProductId();
        Long count = userOrderChangeCountDTO.getCount();
        PrimaryKeyUserOrderProduct primaryKeyUserOrderProduct =
                PrimaryKeyUtil.getPrimaryKeyUserOrderProduct(userOrderId, productId);
        UserOrderProduct userOrderProduct = getUserOrderProduct(primaryKeyUserOrderProduct);
        if (userOrderProduct.getActualProductCount() - count >= 0) {
            Long newCount = userOrderProduct.getActualProductCount() - count;
            userOrderProduct.setActualProductCount(newCount);
            userOrderProductRepository.flush();
            log.info("Значение продукта с id {} успешно уменьшено до {} в заказе с id {}!", productId, newCount, userOrderId);
        }
    }

    @Override
    public BigDecimal showUserOrderProductSum(Long userOrderId) {
        UserOrder userOrder = userOrderService.getUserOrder(userOrderId);
        User user = userOrder.getUser();
        List<UserOrderProduct> userOrderProductList = userOrderProductRepository.findByUserOrderId(userOrderId);
        Stream<UserOrderProduct> userOrderProductStream = userOrderProductList.stream();
        return Calculate.calculateSum(userOrderProductStream, user.getDiscount().getDiscount());
    }

    private UserOrderProduct getUserOrderProduct(PrimaryKeyUserOrderProduct primaryKeyUserOrderProduct) {
        return userOrderProductRepository.findById(primaryKeyUserOrderProduct)
                .orElseThrow(() -> new ResourceNotFound("Продукт либо заказ не найден"));
    }

    @Override
    public void addBagToUserOrderProduct(Bag bag, UserOrder userOrder, User user) {
        UserOrderProduct userOrderProduct = UserOrderProduct.builder()
                .userOrder(userOrder).product(bag.getProduct())
                .productCount(bag.getCount()).actualProductCount(bag.getCount())
                .productPrice(bag.getPrice()).build();
        userOrderProductRepository.save(userOrderProduct);
    }
}
