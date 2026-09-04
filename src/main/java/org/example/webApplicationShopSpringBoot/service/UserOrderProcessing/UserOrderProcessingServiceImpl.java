package org.example.webApplicationShopSpringBoot.service.UserOrderProcessing;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webApplicationShopSpringBoot.dto.dto.BagFormDTO;
import org.example.webApplicationShopSpringBoot.dto.dto.OrderDTO;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrder;
import org.example.webApplicationShopSpringBoot.model.ArchivedUserOrderProduct;
import org.example.webApplicationShopSpringBoot.model.additional.primaryKeys.PrimaryKeyBag;
import org.example.webApplicationShopSpringBoot.model.user.User;
import org.example.webApplicationShopSpringBoot.model.userOrder.OrderStatus;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrder;
import org.example.webApplicationShopSpringBoot.model.userOrder.UserOrderProduct;
import org.example.webApplicationShopSpringBoot.repository.archivedUserOrder.ArchivedUserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.bag.BagRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrder.UserOrderRepository;
import org.example.webApplicationShopSpringBoot.repository.userOrderProduct.UserOrderProductRepository;
import org.example.webApplicationShopSpringBoot.service.Calculate;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrder.ArchivedUserOrderService;
import org.example.webApplicationShopSpringBoot.service.archivedUserOrderProduct.ArchivedUserOrderProductService;
import org.example.webApplicationShopSpringBoot.service.discount.DiscountService;
import org.example.webApplicationShopSpringBoot.service.exceptions.EmptyList;
import org.example.webApplicationShopSpringBoot.service.orderPoint.OrderPointService;
import org.example.webApplicationShopSpringBoot.service.product.ProductService;
import org.example.webApplicationShopSpringBoot.service.user.UserService;
import org.example.webApplicationShopSpringBoot.service.userOrder.UserOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserOrderProcessingServiceImpl implements UserOrderProcessingService {

    private ArchivedUserOrderRepository archivedUserOrderRepository;
    private UserOrderService userOrderService;
    private ArchivedUserOrderProductService archivedUserOrderProductService;
    private ArchivedUserOrderService archivedUserOrderService;
    private UserService userService;
    private DiscountService discountService;
    private OrderPointService orderPointService;
    private UserOrderRepository userOrderRepository;
    private UserOrderProductRepository userOrderProductRepository;
    private ProductService productService;
    private BagRepository bagRepository;

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

    public void createUserOrder(BagFormDTO bagFormDTO, User user) {
        List<OrderDTO> list = userOrderService.toNewOrderDTO(bagFormDTO, user);
        if (list.isEmpty()) {
            throw new EmptyList("Корзина товаров пуста!");
        }
        User managedUser = userService.getUser(list.getFirst().getUserId());
        BigDecimal orderSum = BigDecimal.ZERO;
        UserOrder userOrder = userOrderService.createUserOrder(user, orderPointService.getOrderPoint(list.getFirst().getOrderPointId()));
        userOrderRepository.save(userOrder);
        for (OrderDTO orderDTO : list) {
            if (orderDTO.getCount() != 0) {
                UserOrderProduct userOrderProduct = UserOrderProduct.builder()
                        .userOrder(userOrder).product(productService.getProduct(orderDTO.getProductId()))
                        .productCount(orderDTO.getCount()).actualProductCount(orderDTO.getCount()).productPrice(orderDTO.getProductPrice()).build();

                orderSum = orderSum.add(((orderDTO.getProductPrice()).multiply(new BigDecimal(orderDTO.getCount()))));
                userOrderProductRepository.save(userOrderProduct);
                PrimaryKeyBag primaryKeyBag = new PrimaryKeyBag(orderDTO.getUserId(), orderDTO.getProductId());
                bagRepository.deleteById(primaryKeyBag);
            }
        }
        BigDecimal orderSumWithDiscount = Calculate.orderWithSum(user, orderSum);
        userOrder.setOrderSum(orderSumWithDiscount);
        userOrderRepository.save(userOrder);
        log.info("Заказ с id {} для пользователя с id {} успешно создан!", managedUser.getId(), userOrder.getId());
    }

}
