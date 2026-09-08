package org.example.webApplicationShopSpringBoot.service.UserOrderProcessing;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.converterDTO.UserOrderConverter;
import org.example.webApplicationShopSpringBoot.dto.dto.UserOrderDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.complicatedDTO.ShowOrderDTO;
import org.example.webApplicationShopSpringBoot.model.*;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.repository.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.service.Calculate;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct.ArchivedUserOrderProductService;
import org.example.webApplicationShopSpringBoot.service.bag.BagService;
import org.example.webApplicationShopSpringBoot.service.discount.DiscountService;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointService;
import org.example.webApplicationShopSpringBoot.service.serviceExceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.example.webApplicationShopSpringBoot.service.userOrderProduct.UserOrderProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserOrderProcessingServiceImpl implements UserOrderProcessingService {

    private final ArchivedUserOrderProductService archivedUserOrderProductService;
    private final UserOrderProductService userOrderProductService;
    private final UserOrderService userOrderService;
    private final BagService bagService;
    private final ArchivedUserOrderService archivedUserOrderService;
    private final UserService userService;
    private final DiscountService discountService;
    private final OrderPointService orderPointService;
    private final UserOrderRepository userOrderRepository;
    private final ArchivedUserOrderRepository archivedUserOrderRepository;
    private final UserOrderConverter userOrderConverter;

    @Override
    public void closeUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderService.getUserOrder(userOrderId);
        Set<ArchivedUserOrderProduct> archivedUserOrderProduct = archivedUserOrderProductService.createUserOrderProduct(
                userOrder.getUserOrderProduct(), null);

        BigDecimal finalOrderSum = Calculate.calculateSum(archivedUserOrderProduct.stream(), userOrder.getUser().getDiscount().getDiscount());

        ArchivedUserOrder archivedUserOrder = archivedUserOrderService.createArchivedUserOrder(userOrder, archivedUserOrderProduct, finalOrderSum);
        archivedUserOrderProductService.setArchivedUserOrder(archivedUserOrder, archivedUserOrderProduct);
        archivedUserOrderRepository.save(archivedUserOrder);
        userService.increaseTotalSum(userOrder.getUser().getId(), archivedUserOrder.getFinalOrderSum());
        userOrderService.deleteUserOrder(userOrderId);
        discountService.checkUserDiscount(userOrder.getUser());
        log.info("Заказ с id {} успешно архивирован!", archivedUserOrder.getUserOrderId());
    }

    @Override
    public void refuseUserOrder(Long userOrderId) {
        UserOrder userOrder = userOrderService.getUserOrder(userOrderId);
        Set<ArchivedUserOrderProduct> archivedUserOrderProduct = archivedUserOrderProductService.createUserOrderProduct(
                userOrder.getUserOrderProduct(), null);
        ArchivedUserOrder archivedUserOrder = archivedUserOrderService.refuseUserOrder(userOrder, archivedUserOrderProduct);
        archivedUserOrderProductService.setArchivedUserOrder(archivedUserOrder, archivedUserOrderProduct);
        archivedUserOrderRepository.save(archivedUserOrder);
        userOrderService.deleteUserOrder(userOrderId);
        log.info("Отказ заказа с id {} успешно архивирован!", archivedUserOrder.getUserOrderId());
    }

    @Override
    public void createUserOrder(User user, Long orderPointId) {
        List<Bag> list = bagService.getAllBagsForOrder(user);
        if (list.isEmpty()) {
            throw new EmptyList("Корзина товаров пуста!");
        }
        BigDecimal orderSum = BigDecimal.ZERO;
        OrderPoint orderPoint = orderPointService.getOrderPoint(orderPointId);
        UserOrder userOrder = userOrderService.createUserOrder(user, orderPoint);
        Long userId = user.getId();
        for (Bag bag : list) {
            Long productCount = bag.getCount();
            Product product = bag.getProduct();
            BigDecimal productPrice = product.getPrice();
            userOrderProductService.addBagToUserOrderProduct(bag, userOrder, user);
            orderSum = orderSum.add(((productPrice).multiply(new BigDecimal(productCount))));
        }
        BigDecimal orderSumWithDiscount = Calculate.orderWithSum(user, orderSum);
        userOrder.setOrderSum(orderSumWithDiscount);
        userOrderRepository.save(userOrder);
        bagService.deleteAllUserBags(user);
        log.info("Заказ с id {} для пользователя с id {} успешно создан!", userOrder.getId(), userId);
    }

    @Override
    public ShowOrderDTO returnOrderInfo(Long id) {
        UserOrderDTO userOrderDTO = userOrderConverter.toDTO(userOrderService.getUserOrder(id));
        BigDecimal UserOrderProductSum = userOrderProductService.showUserOrderProductSum(id);
        return ShowOrderDTO.builder().userOrderDTO(userOrderDTO).UserOrderProductSum(UserOrderProductSum).build();
    }
}
